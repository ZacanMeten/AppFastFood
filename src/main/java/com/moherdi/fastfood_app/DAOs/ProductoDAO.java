package com.moherdi.fastfood_app.DAOs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.entities.Producto;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductoDAO implements IProductoDAO {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Producto> getProductos() {
        return em.createQuery("from Producto").getResultList();
    }

    @Transactional
    @Override
    public void saveProducto(Producto producto) {
        int identificadorN = producto.getId_producto();
        if (producto != null && identificadorN > 0) {
            em.merge(producto);
        } else {
            em.persist(producto);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscarProducto(int id) {
        return em.find(Producto.class, id);
    }

    @Override
    @Transactional
    public void borrarProducto(int id) {
        Producto postre = buscarProducto(id);
        em.remove(postre);
    }

    @Override
    @Transactional
    public List<Producto> findByNombre(String nombre) {
        List<Producto> productos = getProductos();
        for (int i = 0; i < productos.size(); i++) {
            Producto e = productos.get(i);
            if(e.getNombre().equalsIgnoreCase(nombre)){
                productos.remove(i);
            }
        }
        return productos;
    }

}
