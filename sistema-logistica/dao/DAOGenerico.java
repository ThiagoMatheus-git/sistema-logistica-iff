package br.edu.ifsul.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class DAOGenerico<T> {

    private final Class<T> classePersistente;

    @PersistenceContext(unitName = "SistemaLogisticaPU")
    protected EntityManager em;

    protected String mensagem = "";

    public DAOGenerico(Class<T> classePersistente) {
        this.classePersistente = classePersistente;
    }

    public void persist(T obj) throws Exception {
        em.persist(obj);
    }

    public void merge(T obj) throws Exception {
        em.merge(obj);
    }

    public void remove(T obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
    }

    public T getObjectById(Integer id) throws Exception {
        return em.find(classePersistente, id);
    }

    public List<T> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }
}
