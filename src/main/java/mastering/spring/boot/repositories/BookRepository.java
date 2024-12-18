package mastering.spring.boot.repositories;

import mastering.spring.boot.models.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @Cacheable("books")
    Optional<Book> findById(Long id);
}
