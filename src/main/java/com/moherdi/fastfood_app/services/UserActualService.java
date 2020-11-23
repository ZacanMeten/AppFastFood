package com.moherdi.fastfood_app.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserActualService {

    public static String obtener_Nombre() {
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


}