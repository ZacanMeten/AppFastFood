package com.moherdi.fastfood_app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PEDIDOS")
public class Pedido implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cli")
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    private Date fecha_emi;

    private String estado;
    private double precio_total;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pedido", nullable = false)
    private List<DetallePedido> detalles;

    public void addDetallePedido(DetallePedido detallePedido) {
        this.detalles.add(detallePedido);
    }

    // Contrus
    public Pedido() {
        this.estado = "PENDIENTE";
        this.detalles = new ArrayList<DetallePedido>();
    }

    @PrePersist
    public void prePersist() {
        this.fecha_emi = new Date();
    }

    // Getters Setters
    public int getId_pedidos() {
        return id_pedidos;
    }

    public void setId_pedidos(int id_pedidos) {
        this.id_pedidos = id_pedidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha_emi() {
        return fecha_emi;
    }

    public void setFecha_emi(Date fecha_emi) {
        this.fecha_emi = fecha_emi;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio_total() {
        Double total = 0.0;
        int n = detalles.size();
        for (int i = 0; i < n; i++) {
            total += detalles.get(i).getPrecio();
        }
        this.precio_total = total;
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

}
