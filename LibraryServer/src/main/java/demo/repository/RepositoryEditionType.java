package demo.repository;

import demo.entity.EditionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с объектами типов изданий БД
 */
@Repository
public interface RepositoryEditionType extends JpaRepository<EditionTypeEntity, Long> {
    /**
     * Находит и возвращает тип издания по его id
     * @param id Long
     * @return EditionTypeEntity
     */
    EditionTypeEntity findEditionTypeById(Long id);
}
