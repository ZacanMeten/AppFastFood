package com.moherdi.fastfood_app.DAOs.interfaces;

import java.util.List;

import com.moherdi.fastfood_app.entities.Producto;

public interface IProductoDAO {

    public List<Producto> getProductos();

    public void saveProducto(Producto producto);

    public Producto buscarProducto(int id);

    public void borrarProducto(int id);

    public List<Producto> findByNombre(String nombre);
}
