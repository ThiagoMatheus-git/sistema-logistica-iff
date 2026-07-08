package br.edu.ifsul.controller;

import br.edu.ifsul.dao.EntregadorDAO;
import br.edu.ifsul.model.Entregador;
import br.edu.ifsul.model.Endereco;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "entregadorController")
@SessionScoped
public class EntregadorController implements Serializable {

    @EJB
    private EntregadorDAO dao;

    private Entregador objeto;
    private String filtroNome = "";
    private List<Entregador> listaFiltrada;
    private List<String> categoriasCNH = Arrays.asList("A", "B", "AB");

    public EntregadorController() {}

    public String listar() {
        listaFiltrada = dao.getListaOrdenadaPorNome();
        return "/privado/entregador/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Entregador();
        objeto.setEndereco(new Endereco());
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Entregador salvo com sucesso!");
            listar();
            objeto = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao salvar entregador: " + Util.getMensagemErro(e));
        }
    }

    public void editar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao carregar entregador: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Integer id) {
        try {
            if (dao.possuiEncomendas(id)) {
                Util.mensagemErro("Não é possível excluir este entregador pois ele está atrelado a encomendas!");
                return;
            }
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Entregador removido com sucesso!");
            listar();
            objeto = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover entregador: " + Util.getMensagemErro(e));
        }
    }

    public void filtrar() {
        listaFiltrada = dao.filtrarPorNome(filtroNome);
    }

    public void limparFiltro() {
        filtroNome = "";
        listar();
    }

    public void cancelar() {
        objeto = null;
    }

    // Getters e Setters
    public Entregador getObjeto() { return objeto; }
    public void setObjeto(Entregador objeto) { this.objeto = objeto; }

    public String getFiltroNome() { return filtroNome; }
    public void setFiltroNome(String filtroNome) { this.filtroNome = filtroNome; }

    public List<Entregador> getListaFiltrada() { return listaFiltrada; }
    public void setListaFiltrada(List<Entregador> listaFiltrada) { this.listaFiltrada = listaFiltrada; }

    public List<String> getCategoriasCNH() { return categoriasCNH; }

    public EntregadorDAO getDao() { return dao; }
    public void setDao(EntregadorDAO dao) { this.dao = dao; }
}
