package com.prueba.trabajosdegrado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prueba.trabajosdegrado.service.FileService;
import jakarta.annotation.Resource;

@SpringBootApplication
public class TrabajosDeGradoApplication implements CommandLineRunner {
	@Resource
	FileService storageService;

	public static void main(String[] args) {
		SpringApplication.run(TrabajosDeGradoApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		// storageService.deleteAll();
		storageService.init();
	}
}