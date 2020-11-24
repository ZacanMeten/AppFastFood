package com.moherdi.fastfood_app.services;

import com.moherdi.fastfood_app.DAOs.interfaces.IClienteDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IStaffDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IUsuarioRepo;
import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.Staff;
import com.moherdi.fastfood_app.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserActualService {

    @Autowired
    private IUsuarioRepo repoUsuario;

    @Autowired
    private IClienteDAO repoCliente;

    @Autowired
    private IStaffDAO repoStaff;

    public String obtener_Nombre() {
        String user;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Obtener usuario Logeado
        if (principal instanceof UserDetails) {
            user = ((UserDetails) principal).getUsername();
        } else {
            user = principal.toString();
        }
        return user;
    }

    public Usuario obtener_Usuario() {
        return repoUsuario.findByNombre(obtener_Nombre());
    }

    public String obtener_nombresC() {
        Cliente c = repoCliente.findByUser(obtener_Usuario());
        Staff s = repoStaff.findByUser(obtener_Usuario());
        if (c != null) {
            return c.getNombres() + c.getApellidos();
        }
        if (s != null) {
            return s.getNombres() + s.getApellidos();
        }
        return "e:UserActS";
    }
}