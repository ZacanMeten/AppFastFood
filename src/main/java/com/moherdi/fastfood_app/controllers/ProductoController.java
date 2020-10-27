package com.moherdi.fastfood_app.controllers;

import java.util.Map;

import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.entities.Producto;
import com.moherdi.fastfood_app.services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/postres")
public class ProductoController {

    @Autowired
    private ProductoService producServ;

    @Autowired
    private IProductoDAO productoDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listarProductos(Model model) {
        model.addAttribute("titulo", "Postres");
        model.addAttribute("postres", productoDAO.getProductos());
        return "postre/postres";
    }

    @RequestMapping(value = "/new")
    public String crearProducto(Map<String, Object> map) {
        Producto nuevo = new Producto();
        map.put("postre", nuevo);
        map.put("titulo", "Formulario de nuevo Postre");
        return "postre/formulario";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String guardarProducto(@RequestParam("file") MultipartFile file, Producto producto) {
        producServ.saveProducto_toBD(file, producto);
        return "redirect:list";
    }

    @RequestMapping(value = "/new/{id}")
    public String editarProducto(@PathVariable(value = "id") int id, Map<String, Object> map) {
        Producto producto = null;
        if (id > 0) {
            producto = productoDAO.buscarProducto(id);
        } else {
            return "redirect:list";
        }
        map.put("postre", producto);
        map.put("titulo", "Editar Postre");
        return "postre/formulario";
    }

    @RequestMapping(value = "/delete/{id}")
    public String eliminar(@PathVariable(value = "id") int id) {
        if (id > 0) {
            productoDAO.borrarProducto(id);
        }
        return "redirect:/postres/list";
    }

}
