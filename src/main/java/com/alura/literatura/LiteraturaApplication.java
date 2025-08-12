package com.alura.literatura;

import com.alura.literatura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	private final Principal principal;   // <-- referencia al bean

	public LiteraturaApplication(Principal principal) {  // <-- inyección por constructor
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraElMenu();       // <-- úsalo
	}
}
