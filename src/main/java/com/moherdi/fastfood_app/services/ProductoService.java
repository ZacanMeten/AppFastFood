package com.moherdi.fastfood_app.services;

import java.io.IOException;
import java.util.Base64;

import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.entities.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    @Autowired
    private IProductoDAO productoDAO;

    @Transactional
    public void saveProducto_toBD(MultipartFile file, Producto producto) {

        Producto nuevo = producto;
        String nombre_archivo = StringUtils.cleanPath(file.getOriginalFilename());
        if (nombre_archivo.contains("..")) {
            System.out.println("archivo no valido");
        }
        try {

            if (file.getSize() > 0 && file.getContentType().startsWith("image/")) {
                nuevo.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } else {
                if (producto.getId_producto() > 0) {
                    nuevo.setImage(productoDAO.buscarProducto(producto.getId_producto()).getImage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productoDAO.saveProducto(nuevo);
    }
}
