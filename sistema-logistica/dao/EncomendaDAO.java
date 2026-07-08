package br.edu.ifsul.dao;

import br.edu.ifsul.model.Encomenda;
import br.edu.ifsul.model.Entregador;
import br.edu.ifsul.model.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateful
public class EncomendaDAO extends DAOGenerico<Encomenda> implements Serializable {

    public EncomendaDAO() {
        super(Encomenda.class);
    }

    public List<Encomenda> getListaOrdenadaPorCliente() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Encomenda> cq = cb.createQuery(Encomenda.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        Join<Encomenda, Cliente> joinCliente = root.join("cliente");
        cq.orderBy(cb.asc(joinCliente.get("nome")));
        return em.createQuery(cq).getResultList();
    }

    public List<Encomenda> relatorioFiltrado(Integer entregadorId, String cidade, Double valorMaximo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Encomenda> cq = cb.createQuery(Encomenda.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        Join<Encomenda, Cliente> joinCliente = root.join("cliente");
        Join<Cliente, ?> joinEndereco = joinCliente.join("endereco");
        Join<Encomenda, Entregador> joinEntregador = root.join("entregador");

        List<Predicate> predicates = new ArrayList<>();

        if (entregadorId != null) {
            predicates.add(cb.equal(joinEntregador.get("id"), entregadorId));
        }

        if (cidade != null && !cidade.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(joinEndereco.get("cidade")), "%" + cidade.toLowerCase() + "%"));
        }

        if (valorMaximo != null) {
            predicates.add(cb.lt(root.get("valor"), valorMaximo));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        cq.orderBy(cb.asc(joinCliente.get("nome")));
        return em.createQuery(cq).getResultList();
    }
}
