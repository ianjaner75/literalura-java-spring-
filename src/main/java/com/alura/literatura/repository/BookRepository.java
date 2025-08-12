package com.alura.literatura.repository;

import com.alura.literatura.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByGutendexId(Integer gutendexId);
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findByLanguageIgnoreCase(String lang);
    List<Book> findByTitleContainingIgnoreCase(String q);
    @Query("""
         select distinct b
         from Book b
         left join fetch b.authors
         order by b.title
         """)
    List<Book> findAllWithAuthorsOrderByTitle();
}
