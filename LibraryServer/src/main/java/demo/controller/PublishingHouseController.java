package demo.controller;

import demo.entity.PublishingHouseEntity;
import demo.repository.RepositoryPublishingHouse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST контроллер для взаимодействия с базой данных по издательствам
 */
@RestController
@RequestMapping("publishing_houses/")
public class PublishingHouseController {
    private final RepositoryPublishingHouse repositoryPublishingHouse;

    /**
     * Инициализатор класса
     * @param repositoryPublishingHouse RepositoryPublishingHouse
     */
    public PublishingHouseController(RepositoryPublishingHouse repositoryPublishingHouse) {
        this.repositoryPublishingHouse = repositoryPublishingHouse;
    }

    /**
     * Возвращает все найденнные в бд записи по издательствам
     * @return String
     */
    @GetMapping("/AllPublishingHouses")
    public List<PublishingHouseEntity> getAllHouses() {
        return repositoryPublishingHouse.findAll();
    }
}
