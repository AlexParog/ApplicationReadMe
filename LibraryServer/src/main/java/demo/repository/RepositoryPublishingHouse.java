package demo.repository;

import demo.entity.PublishingHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с объектами выданных книг БД
 */
@Repository
public interface RepositoryPublishingHouse extends JpaRepository<PublishingHouseEntity, Long> {
    /**
     * находит и возвращает выданную книгу по ее id
     * @param id Long
     * @return IssueOfBookEntity
     */
    PublishingHouseEntity findPublishingHouseById(Long id);
}
