package br.edu.ifsul.controller;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.dao.EncomendaDAO;
import br.edu.ifsul.dao.EntregadorDAO;
import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Encomenda;
import br.edu.ifsul.model.Entregador;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "encomendaController")
@SessionScoped
public class EncomendaController implements Serializable {

    @EJB
    private EncomendaDAO dao;

    @EJB
    private ClienteDAO clienteDAO;

    @EJB
    private EntregadorDAO entregadorDAO;

    private Encomenda objeto;
    private Integer clienteId;
    private Integer entregadorId;
    private List<Encomenda> lista;

    public EncomendaController() {}

    public String listar() {
        lista = dao.getListaOrdenadaPorCliente();
        return "/privado/encomenda/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Encomenda();
        clienteId = null;
        entregadorId = null;
    }

    public void salvar() {
        try {
            if (clienteId == null || entregadorId == null) {
                Util.mensagemErro("Selecione um cliente e um entregador!");
                return;
            }
            objeto.setCliente(clienteDAO.getObjectById(clienteId));
            objeto.setEntregador(entregadorDAO.getObjectById(entregadorId));

            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Encomenda registrada com sucesso!");
            listar();
            objeto = null;
            clienteId = null;
            entregadorId = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao salvar encomenda: " + Util.getMensagemErro(e));
        }
    }

    public void editar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            clienteId = objeto.getCliente().getId();
            entregadorId = objeto.getEntregador().getId();
        } catch (Exception e) {
            Util.mensagemErro("Erro ao carregar encomenda: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Encomenda removida com sucesso!");
            listar();
            objeto = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover encomenda: " + Util.getMensagemErro(e));
        }
    }

    public void cancelar() {
        objeto = null;
        clienteId = null;
        entregadorId = null;
    }

    // Getters e Setters
    public Encomenda getObjeto() { return objeto; }
    public void setObjeto(Encomenda objeto) { this.objeto = objeto; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public Integer getEntregadorId() { return entregadorId; }
    public void setEntregadorId(Integer entregadorId) { this.entregadorId = entregadorId; }

    public List<Encomenda> getLista() { return lista; }
    public void setLista(List<Encomenda> lista) { this.lista = lista; }

    public List<Cliente> getListaClientes() {
        return clienteDAO.getListaOrdenadaPorNome();
    }

    public List<Entregador> getListaEntregadores() {
        return entregadorDAO.getListaOrdenadaPorNome();
    }
}
