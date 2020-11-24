package com.moherdi.fastfood_app.controllers;

import java.util.Map;

import com.moherdi.fastfood_app.DAOs.interfaces.IClienteDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IUsuarioRepo;
import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.Usuario;
import com.moherdi.fastfood_app.services.UserActualService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private UserActualService actualUsuario;

    @GetMapping(value = "/successlogin")
    public String getTipoUsuario() {
        // Buscar si es cliente o Staff
        Cliente isCliente = repoCliente.findByUser(actualUsuario.obtener_Usuario());

        if (isCliente != null) {
            return "redirect:/catalogo";// Redirige al apagina de inico del Cliente
        } else {
            return "redirect:/inicio";// Redirige a la pagina de inico del Staff
        }

    }

    @GetMapping(value = { "/inicio" })
    public String inicio(Model model) {
        model.addAttribute("username", actualUsuario.obtener_Nombre());
        return "inicio";
    }

    @RequestMapping(value = { "/", "/catalogo" }, method = RequestMethod.GET)
    public String paginaPrincipalCliente(Model model) {
        Cliente cli = repoCliente.findByUser(actualUsuario.obtener_Usuario());
        model.addAttribute("cliente", cli);
        model.addAttribute("titulo", "Catalogo de Postres");
        model.addAttribute("postres", daoProducto.getProductos());
        return "catalogo";
    }

    // Usuario
    @RequestMapping(value = "/signup")
    public String nuevoCliente(Map<String, Object> map) {
        Cliente c = new Cliente();
        map.put("cliente", c);
        return "usuarios/cliente_form";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String subirUsuario(Cliente cliente, @RequestParam(name = "username") String username,
            @RequestParam(name = "contrasenia") String contrasenia) {
        Usuario us = new Usuario();
        us.setNombre(username);
        us.setContrasenia(encoder.encode(contrasenia));
        repoUser.save(us);
        // Guardar en el repositorio
        cliente.setUser(us);
        repoCliente.save(cliente);
        return "redirect:/login";
    }

}