package br.edu.ifsul.controller;

import br.edu.ifsul.dao.ClienteDAO;
import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Endereco;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    @EJB
    private ClienteDAO dao;

    private Cliente objeto;
    private String filtroNome = "";
    private List<Cliente> listaFiltrada;

    public ClienteController() {}

    public String listar() {
        listaFiltrada = dao.getListaOrdenadaPorNome();
        return "/privado/cliente/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Cliente();
        objeto.setEndereco(new Endereco());
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                // Cadastro novo - verificar CPF duplicado
                if (dao.cpfJaExiste(objeto.getCpf())) {
                    Util.mensagemErro("Já existe um cliente cadastrado com este CPF!");
                    return;
                }
                dao.persist(objeto);
            } else {
                // Edição - não permite alterar CPF (já está desabilitado na tela)
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Cliente salvo com sucesso!");
            listar();
            objeto = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao salvar cliente: " + Util.getMensagemErro(e));
        }
    }

    public void editar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao carregar cliente: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Cliente removido com sucesso!");
            listar();
            objeto = null;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover cliente: " + Util.getMensagemErro(e));
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
    public Cliente getObjeto() { return objeto; }
    public void setObjeto(Cliente objeto) { this.objeto = objeto; }

    public String getFiltroNome() { return filtroNome; }
    public void setFiltroNome(String filtroNome) { this.filtroNome = filtroNome; }

    public List<Cliente> getListaFiltrada() { return listaFiltrada; }
    public void setListaFiltrada(List<Cliente> listaFiltrada) { this.listaFiltrada = listaFiltrada; }

    public ClienteDAO getDao() { return dao; }
    public void setDao(ClienteDAO dao) { this.dao = dao; }
}
