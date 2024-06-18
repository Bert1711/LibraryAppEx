package com.zaroyan.libraryappex.repositories;

import com.zaroyan.libraryappex.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zaroyan
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
