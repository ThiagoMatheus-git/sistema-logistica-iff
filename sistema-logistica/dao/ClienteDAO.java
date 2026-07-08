package br.edu.ifsul.dao;

import br.edu.ifsul.model.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateful
public class ClienteDAO extends DAOGenerico<Cliente> implements Serializable {

    public ClienteDAO() {
        super(Cliente.class);
    }

    public boolean cpfJaExiste(String cpf) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("cpf"), cpf));
        return em.createQuery(cq).getSingleResult() > 0;
    }

    public boolean cpfJaExisteOutroId(String cpf, Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.select(cb.count(root));
        Predicate p1 = cb.equal(root.get("cpf"), cpf);
        Predicate p2 = cb.notEqual(root.get("id"), id);
        cq.where(cb.and(p1, p2));
        return em.createQuery(cq).getSingleResult() > 0;
    }

    public List<Cliente> getListaOrdenadaPorNome() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.orderBy(cb.asc(root.get("nome")));
        return em.createQuery(cq).getResultList();
    }

    public List<Cliente> filtrarPorNome(String nome) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> root = cq.from(Cliente.class);
        if (nome != null && !nome.trim().isEmpty()) {
            cq.where(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        cq.orderBy(cb.asc(root.get("nome")));
        return em.createQuery(cq).getResultList();
    }
}
