package demo.repository;

import demo.entity.AuthorEntity;
import demo.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с объектами книг БД
 */
@Repository
public interface RepositoryBook extends JpaRepository<BookEntity, Long> {
    /**
     * находит и возвращает книгу по ее id
     * @param id Long
     * @return BookEntity
     */
    BookEntity findBooksById(Long id);

    /**
     * находит все книги по их автору
     * @param author AuthorEntity
     * @return List of BookEntities
     */
    List<BookEntity> findAllByAuthor(AuthorEntity author);
}
