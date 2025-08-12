package com.alura.literatura.principal;

import com.alura.literatura.domain.Author;
import com.alura.literatura.service.CatalogService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.stream.Collectors;

@Component  // para que Spring la gestione e inyecte CatalogService
public class Principal {

    private final CatalogService catalog;
    private final Scanner sc = new Scanner(System.in);

    public Principal(CatalogService catalog) {
        this.catalog = catalog;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                
                === LiterAlura ===
                1) Buscar y registrar libro por título
                2) Listar libros registrados
                3) Listar autores registrados
                4) Listar autores vivos en un año
                5) Listar libros por idioma (ES/EN/FR/PT)
                0) Salir
                """);
            System.out.print("Opción: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty() || !input.chars().allMatch(Character::isDigit)) {
                System.out.println("Opción inválida"); continue;
            }
            opcion = Integer.parseInt(input);

            switch (opcion) {
                case 1 -> buscarYRegistrarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarYRegistrarLibro() {
        System.out.print("Título a buscar: ");
        String q = sc.nextLine().trim();
        if (q.isEmpty()) { System.out.println("Título vacío."); return; }
        System.out.println(catalog.searchAndSaveByTitle(q));
    }

    private void listarLibros() {
        var list = catalog.listBooksWithAuthors(); // usa JOIN FETCH (evita LazyInitialization)
        if (list.isEmpty()) { System.out.println("No hay libros."); return; }
        list.forEach(b -> {
            String autores = b.getAuthors().stream()
                    .map(Author::getName)
                    .collect(Collectors.joining("; "));
            System.out.printf("- %s | Lang: %s | Descargas: %s | Autores: %s%n",
                    b.getTitle(), b.getLanguage(), b.getDownloadCount(), autores);
        });
    }

    private void listarAutores() {
        var list = catalog.listAuthors();
        if (list.isEmpty()) { System.out.println("No hay autores."); return; }
        list.forEach(a -> System.out.printf("- %s (%s–%s)%n",
                a.getName(),
                a.getBirthYear() == null ? "?" : a.getBirthYear(),
                a.getDeathYear() == null ? "?" : a.getDeathYear()));
    }

    private void listarAutoresVivos() {
        System.out.print("Año: ");
        String y = sc.nextLine().trim();
        try {
            int year = Integer.parseInt(y);
            var list = catalog.listAliveIn(year);
            if (list.isEmpty()) { System.out.println("Sin autores vivos en ese año."); return; }
            list.forEach(a -> System.out.printf("- %s (%s–%s)%n",
                    a.getName(),
                    a.getBirthYear() == null ? "?" : a.getBirthYear(),
                    a.getDeathYear() == null ? "?" : a.getDeathYear()));
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
        }
    }

    private void listarPorIdioma() {
        System.out.print("Idioma (ES/EN/FR/PT): ");
        String lang = sc.nextLine().trim();
        var list = catalog.listByLanguage(lang);
        if (list.isEmpty()) { System.out.println("Sin libros para ese idioma."); return; }
        list.forEach(b -> System.out.printf("- %s (%s)%n", b.getTitle(), b.getLanguage()));
    }
}
