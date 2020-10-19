package com.moherdi.fastfood_app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTOS")
public class Producto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;

    private String nombre;
    private double precio_unit;
    private int dsct_prc;
    private String image_data;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio_unit() {
        return precio_unit;
    }

    public void setPrecio_unit(double precio_unit) {
        double limiteS = 99999.9;
        if(precio_unit < 0){
            precio_unit = 0;
        }
        if(precio_unit > limiteS){
            precio_unit = limiteS;
        }
        this.precio_unit = precio_unit;
    }

    public int getDsct_prc() {
        return dsct_prc;
    }

    public void setDsct_prc(int dsct_prc) {
        if(dsct_prc < 0)    dsct_prc=0;
        if(dsct_prc > 70)   dsct_prc=70;
        this.dsct_prc = dsct_prc;
    }

    public String getImage() {
        return image_data;
    }

    public void setImage(String image) {
        this.image_data = image;
    }

    
}
