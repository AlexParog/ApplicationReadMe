package demo.repository;

import demo.entity.BookEntity;
import demo.entity.ExemplarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с объектами экземпляров БД
 */
@Repository
public interface RepositoryExemplar extends JpaRepository<ExemplarEntity, Long> {
    /**
     * Находит и возвращает экземпляр по его id
     * @param id Long
     * @return AuthorEntity
     */
    ExemplarEntity findExemplarById(Long id);

    /**
     * Находи и возвращает список экземпляров одной книги
     * @param bookEntity BookEntity
     * @return List из ExemplarEntity
     */
    List<ExemplarEntity> findAllByBookEntity(BookEntity bookEntity);
}
