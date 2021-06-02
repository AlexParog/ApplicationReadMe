package demo.repository;

import demo.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с объектами выданных книг БД
 */
@Repository
public interface RepositoryPosition extends JpaRepository<PositionEntity, Long> {
    /**
     * находит и возвращает выданную книгу по ее id
     * @param id Long
     * @return IssueOfBookEntity
     */
    PositionEntity findPositionById(Long id);
}
