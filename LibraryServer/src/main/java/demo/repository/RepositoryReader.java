package demo.repository;

import demo.entity.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с объектами выданных книг БД
 */
@Repository
public interface RepositoryReader extends JpaRepository<ReaderEntity, Long> {
    /**
     * находит и возвращает выданную книгу по ее id
     * @param id Long
     * @return IssueOfBookEntity
     */
    ReaderEntity findReaderById(Long id);

    /**
     * находит и возвращает читателя  по его имени
     * @param username String
     * @return ReaderEntity
     */
    ReaderEntity findByFirstName(String username);

    /**
     * находит и возвращает читателя  по его телефону
     * @param telephone String
     * @return ReaderEntity
     */
    ReaderEntity findReaderEntityByTelephone(String telephone);
}
