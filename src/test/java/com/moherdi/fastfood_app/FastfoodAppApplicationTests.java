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
		Usuario us = new Usuario();
		us.setId_user(99);
		us.setNombre("anthony");
		//Encriptando la contrasenia
		us.setContrasenia(encoder.encode("anthony1"));

		Usuario retorno = repo.save(us);

		assertTrue(retorno.getNombre().equalsIgnoreCase(us.getNombre()));
	}
}
