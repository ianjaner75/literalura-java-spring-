package com.alura.literatura.domain;

import jakarta.persistence.*;

import com.alura.literatura.domain.Book;
import jakarta.persistence.*;  // etc.
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "authors")
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;        // "Austen, Jane"

    private Integer birthYear;  // puede ser null
    private Integer deathYear;  // puede ser null

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new LinkedHashSet<>();

    // getters/setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }
    public Set<Book> getBooks() { return books; }
}
