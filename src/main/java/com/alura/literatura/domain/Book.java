package com.alura.literatura.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import com.alura.literatura.domain.Author;


@Entity @Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer gutendexId;     // para evitar duplicados

    @Column(nullable = false)
    private String title;

    @Column(length = 5)
    private String language;        // "EN", "ES", etc.

    private Integer downloadCount;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new LinkedHashSet<>();

    // getters/setters
    public Long getId() { return id; }
    public Integer getGutendexId() { return gutendexId; }
    public void setGutendexId(Integer gutendexId) { this.gutendexId = gutendexId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
    public Set<Author> getAuthors() { return authors; }
}
