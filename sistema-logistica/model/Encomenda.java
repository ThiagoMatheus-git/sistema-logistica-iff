package br.edu.ifsul.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "encomenda")
public class Encomenda implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_encomenda", sequenceName = "seq_encomenda_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_encomenda", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "codigo_rastreio", nullable = false, length = 50)
    private String codigoRastreio;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "entregador_id", nullable = false)
    private Entregador entregador;

    public Encomenda() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigoRastreio() { return codigoRastreio; }
    public void setCodigoRastreio(String codigoRastreio) { this.codigoRastreio = codigoRastreio; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Entregador getEntregador() { return entregador; }
    public void setEntregador(Entregador entregador) { this.entregador = entregador; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Encomenda)) return false;
        Encomenda other = (Encomenda) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}
