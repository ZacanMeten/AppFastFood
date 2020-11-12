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
    private int id_pedido;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_staff")
    private Staff staff;

    public void addDetallePedido(DetallePedido detallePedido) {
        this.detalles.add(detallePedido);
    }

    // Contrus
    public Pedido() {
        this.estado = "PENDIENTE";
        this.staff = null;
        this.detalles = new ArrayList<DetallePedido>();
    }

    @PrePersist
    public void prePersist() {
        this.fecha_emi = new Date();
    }

    // Getters Setters
    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

}
