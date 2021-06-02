package demo.repository;

import demo.entity.ExemplarEntity;
import demo.entity.IssueOfBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с объектами выданных книг БД
 */
@Repository
public interface RepositoryIssueOfBook extends JpaRepository<IssueOfBookEntity, Long> {
    /**
     * находит и возвращает выданную книгу по ее id
     * @param id Long
     * @return IssueOfBookEntity
     */
    IssueOfBookEntity findIssueOfBookByExemplarEntityId(Long id);

    /**
     * Возвращает все выбачи по переданному экземпляру
     * @param exemplarEntity ExemplarEntity
     * @return List из IssueOfBookEntity
     */
    List<IssueOfBookEntity> findAllByExemplarEntity(ExemplarEntity exemplarEntity);
}
