package demo.repository;

import demo.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для работы с объектами автров БД
 */
@Repository
public interface RepositoryAuthor extends JpaRepository<AuthorEntity, Long> {
    /**
     * Находит и возвращает автора по его id
     * @param id Long
     * @return AuthorEntity
     */
    AuthorEntity findAuthorById(Long id);
}
