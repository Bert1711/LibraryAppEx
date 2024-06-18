package com.zaroyan.libraryappex.repositories;

import com.zaroyan.libraryappex.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zaroyan
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
