package demo.repository;

import demo.entity.CoWorkersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с объектами работников БД
 */
@Repository
public interface RepositoryCoWorkers extends JpaRepository<CoWorkersEntity, Long> {
    /**
     * находит и возвращает работника по его id
     * @param id Long
     * @return CoWorkersEntity
     */
    CoWorkersEntity findCoWorkersById(Long id);

    /**
     * находит и возвращает работника по его логину
     * @param login String
     * @return CoWorkersEntity
     */
    CoWorkersEntity findCoWorkersEntityByLogin(String login);

    /**
     * находит и возвращает работника по его номеру телефона
     * @param telephone String
     * @return CoWorkersEntity
     */
    CoWorkersEntity findCoWorkersEntityByTelephone(String telephone);
}
