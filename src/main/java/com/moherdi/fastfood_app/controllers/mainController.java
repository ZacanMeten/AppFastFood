package com.moherdi.fastfood_app.controllers;

import java.util.Map;

import com.moherdi.fastfood_app.DAOs.interfaces.IClienteDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IUsuarioRepo;
import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class mainController {

    @Autowired
    private IUsuarioRepo repoUser;

    @Autowired
    private IClienteDAO repoCliente;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IProductoDAO daoProducto;

    private Usuario usuarioActual;

    @GetMapping(value = { "/", "/home", "/inicio" })
    public String inicio() {
        return "inicio";
    }

    @RequestMapping(value = "/catalogo", method = RequestMethod.GET)
    public String listarProductos(Model model) {
        model.addAttribute("titulo", "Postres");
        model.addAttribute("postres", daoProducto.getProductos());
        return "catalogo";
    }

    // Usuario
    @RequestMapping(value = "/signup")
    public String nuevoUsuario(Map<String, Object> map) {
        Usuario usuario_nuevo = new Usuario();
        map.put("usuario", usuario_nuevo);
        return "usuarios/usuario_form";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String subirUsuario(Usuario usuario) {
        usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));
        repoUser.save(usuario);
        usuarioActual = usuario;
        System.out.println("ID de usuario"+usuario.getId_user());
        return "redirect:/signup/cliente";
    }

    // Cliente
    @RequestMapping(value = "/signup/cliente")
    public String crearCliente(Map<String, Object> map) {
        map.put("cliente", new Cliente());
        return "usuarios/cliente_form";
    }

    @RequestMapping(value = "/signup/cliente", method = RequestMethod.POST)
    public String guardar(Cliente cliente) {
        cliente.setUser(this.usuarioActual);
        repoCliente.save(cliente);
        return "redirect:/";
    }

}
