package br.edu.ifsul.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa implements Serializable {

    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, 
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Encomenda> encomendas = new ArrayList<>();

    public Cliente() {}

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public List<Encomenda> getEncomendas() { return encomendas; }
    public void setEncomendas(List<Encomenda> encomendas) { this.encomendas = encomendas; }

    public void adicionarEncomenda(Encomenda obj) {
        obj.setCliente(this);
        this.encomendas.add(obj);
    }

    public void removerEncomenda(int index) {
        this.encomendas.remove(index);
    }
}
