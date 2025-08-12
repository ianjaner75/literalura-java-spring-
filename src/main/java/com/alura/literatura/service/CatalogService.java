package com.alura.literatura.service;

import com.alura.literatura.client.dto.GutendexAuthor;
import com.alura.literatura.client.GutendexClient;
import com.alura.literatura.client.dto.GutendexResponse;
import com.alura.literatura.domain.*;
import com.alura.literatura.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final BookRepository books;
    private final AuthorRepository authors;
    private final GutendexClient client = new GutendexClient();

    public CatalogService(BookRepository books, AuthorRepository authors) {
        this.books = books; this.authors = authors;
    }
    @Transactional(readOnly = true) // opcional, pero recomendable
    public java.util.List<Book> listBooksWithAuthors() {
        return books.findAllWithAuthorsOrderByTitle();
    }
    @Transactional
    public String searchAndSaveByTitle(String title) {
        GutendexResponse resp = client.search(title);
        if (resp.count() == 0 || resp.results().isEmpty()) {
            return "❌ Libro no encontrado en Gutendex.";
        }
        var b = resp.results().get(0);
        if (books.existsByGutendexId(b.id())) {
            return "⚠️ Ese libro ya está registrado (ID " + b.id() + ").";
        }

        var book = new Book();
        book.setGutendexId(b.id());
        book.setTitle(b.title());
        book.setLanguage(b.languages() != null && !b.languages().isEmpty()
                ? b.languages().get(0).toUpperCase() : null);
        book.setDownloadCount(b.downloadCount());

        if (b.authors() != null) {
            for (GutendexAuthor ga : b.authors()) {
                var author = authors.findByNameAndBirthYear(ga.name(), ga.birthYear())
                        .orElseGet(() -> {
                            var a = new Author();
                            a.setName(ga.name());
                            a.setBirthYear(ga.birthYear());
                            a.setDeathYear(ga.deathYear());
                            return authors.save(a);
                        });
                book.getAuthors().add(author);
            }
        }

        books.save(book);
        var autores = book.getAuthors().stream().map(Author::getName).collect(Collectors.joining("; "));
        return "✅ Registrado: " + book.getTitle() + " | " + book.getLanguage()
                + " | Descargas: " + book.getDownloadCount()
                + " | Autores: " + autores;
    }

    public java.util.List<Book> listBooks() { return books.findAllByOrderByTitleAsc(); }
    public java.util.List<Author> listAuthors() { return authors.findAllByOrderByNameAsc(); }
    public java.util.List<Author> listAliveIn(int year) { return authors.findAliveInYear(year); }
    public java.util.List<Book> listByLanguage(String lang) { return books.findByLanguageIgnoreCase(lang); }
}
