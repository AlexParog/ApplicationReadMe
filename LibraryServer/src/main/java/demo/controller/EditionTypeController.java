package demo.controller;

import demo.entity.EditionTypeEntity;
import demo.repository.RepositoryEditionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST контроллер для взаимодействия с базой данных по типам изданий
 */
@RestController
@RequestMapping("editions/")
public class EditionTypeController {
    private final RepositoryEditionType repositoryEditionType;

    /**
     * Инициализатор класса
     * @param repositoryEditionType RepositoryEditionType
     */
    public EditionTypeController(RepositoryEditionType repositoryEditionType) {
        this.repositoryEditionType = repositoryEditionType;
    }

    /**
     * Возвращает все найденнные в бд записи по типам изданий
     * @return String
     */
    @GetMapping("/AllEditions")
    public List<EditionTypeEntity> getAllEditions() {
        return repositoryEditionType.findAll();
    }
}
