package com.moherdi.fastfood_app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moherdi.fastfood_app.DAOs.interfaces.IUsuarioRepo;
import com.moherdi.fastfood_app.entities.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class FastfoodAppApplicationTests {

	@Autowired
	private IUsuarioRepo repo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Test
	void contextLoads() {
	}

	@Test
	void crearUsuario() {
		// Creando el Usuario
		Usuario us = new Usuario();
		us.setNombre("CarlosD");
		// Encriptando la contrasenia
		us.setContrasenia(encoder.encode("CarlosDiaz"));

		Usuario retorno = repo.save(us);
		System.out.println("Usuario: "+us.getNombre()+" - Contraseña: "+us.getContrasenia());
		assertTrue(retorno.getNombre().equalsIgnoreCase(us.getNombre()));
	}
}
