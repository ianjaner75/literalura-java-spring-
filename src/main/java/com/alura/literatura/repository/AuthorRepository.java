package com.alura.literatura.repository;

import com.alura.literatura.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameAndBirthYear(String name, Integer birthYear);

    @Query("""
         select a from Author a
         where (a.birthYear is null or a.birthYear <= :year)
           and (a.deathYear is null or a.deathYear >= :year)
         """)
    List<Author> findAliveInYear(int year);

    List<Author> findAllByOrderByNameAsc();
}
