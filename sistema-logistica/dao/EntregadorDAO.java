package br.edu.ifsul.dao;

import br.edu.ifsul.model.Entregador;
import br.edu.ifsul.model.Encomenda;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateful
public class EntregadorDAO extends DAOGenerico<Entregador> implements Serializable {

    public EntregadorDAO() {
        super(Entregador.class);
    }

    public boolean possuiEncomendas(Integer entregadorId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("entregador").get("id"), entregadorId));
        return em.createQuery(cq).getSingleResult() > 0;
    }

    public List<Entregador> getListaOrdenadaPorNome() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entregador> cq = cb.createQuery(Entregador.class);
        Root<Entregador> root = cq.from(Entregador.class);
        cq.orderBy(cb.asc(root.get("nome")));
        return em.createQuery(cq).getResultList();
    }

    public List<Entregador> filtrarPorNome(String nome) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entregador> cq = cb.createQuery(Entregador.class);
        Root<Entregador> root = cq.from(Entregador.class);
        if (nome != null && !nome.trim().isEmpty()) {
            cq.where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        cq.orderBy(cb.asc(root.get("nome")));
        return em.createQuery(cq).getResultList();
    }
}
