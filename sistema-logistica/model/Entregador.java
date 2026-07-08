package br.edu.ifsul.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "entregador")
public class Entregador extends Pessoa implements Serializable {

    @Column(name = "cnh", nullable = false, length = 20)
    private String cnh;

    @Column(name = "categoria_cnh", nullable = false, length = 2)
    private String categoriaCnh;

    @OneToMany(mappedBy = "entregador", cascade = CascadeType.ALL, 
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Encomenda> encomendas = new ArrayList<>();

    public Entregador() {}

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    public String getCategoriaCnh() { return categoriaCnh; }
    public void setCategoriaCnh(String categoriaCnh) { this.categoriaCnh = categoriaCnh; }

    public List<Encomenda> getEncomendas() { return encomendas; }
    public void setEncomendas(List<Encomenda> encomendas) { this.encomendas = encomendas; }

    public void adicionarEncomenda(Encomenda obj) {
        obj.setEntregador(this);
        this.encomendas.add(obj);
    }

    public void removerEncomenda(int index) {
        this.encomendas.remove(index);
    }
}
