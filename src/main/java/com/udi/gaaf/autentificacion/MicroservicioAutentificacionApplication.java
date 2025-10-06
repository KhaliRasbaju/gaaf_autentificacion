package com.udi.gaaf.autentificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicioAutentificacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioAutentificacionApplication.class, args);
	}

}
