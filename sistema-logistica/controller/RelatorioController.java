package br.edu.ifsul.controller;

import br.edu.ifsul.dao.EncomendaDAO;
import br.edu.ifsul.dao.EntregadorDAO;
import br.edu.ifsul.model.Encomenda;
import br.edu.ifsul.model.Entregador;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "relatorioController")
@SessionScoped
public class RelatorioController implements Serializable {

    @EJB
    private EncomendaDAO encomendaDAO;

    @EJB
    private EntregadorDAO entregadorDAO;

    private Integer entregadorId;
    private String cidade = "";
    private Double valorMaximo;
    private List<Encomenda> resultado;

    public RelatorioController() {}

    public String listar() {
        return "/privado/relatorio/listar?faces-redirect=true";
    }

    public void gerarRelatorio() {
        Integer id = entregadorId;
        if (id != null && id == -1) {
            id = null; // "Todos os Entregadores"
        }
        String cidadeFiltro = cidade;
        if (cidadeFiltro != null && cidadeFiltro.trim().isEmpty()) {
            cidadeFiltro = null;
        }
        Double valor = valorMaximo;
        if (valor != null && valor <= 0) {
            valor = null;
        }
        resultado = encomendaDAO.relatorioFiltrado(id, cidadeFiltro, valor);
    }

    public void limpar() {
        entregadorId = null;
        cidade = "";
        valorMaximo = null;
        resultado = null;
    }

    // Getters e Setters
    public Integer getEntregadorId() { return entregadorId; }
    public void setEntregadorId(Integer entregadorId) { this.entregadorId = entregadorId; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public Double getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(Double valorMaximo) { this.valorMaximo = valorMaximo; }

    public List<Encomenda> getResultado() { return resultado; }
    public void setResultado(List<Encomenda> resultado) { this.resultado = resultado; }

    public List<Entregador> getListaEntregadores() {
        return entregadorDAO.getListaOrdenadaPorNome();
    }
}
