package demo.controller;

import demo.entity.ReaderEntity;
import demo.exception.ReaderAlreadyExistException;
import demo.repository.RepositoryReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для взаимодействия с базой данных по читателям
 */
@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final RepositoryReader repositoryReader;

    /**
     * Инициализатор класса
     * @param repositoryReader RepositoryReader
     */
    public ReaderController(RepositoryReader repositoryReader){
        this.repositoryReader=repositoryReader;
    }

    /**
     * Регистрирует читателя, если его еще нет в базе. Иначе возвращает нужный ответ от сервера
     * @param user ReaderEntity
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity registration(@RequestBody ReaderEntity user) {
        try {
            if (repositoryReader.findByFirstName(user.getFirstName()) != null) {
                throw new ReaderAlreadyExistException("Пользователь с таким именем существует");
            }
            repositoryReader.save(user);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (ReaderAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    /**
     * Находит и возвращает читателя по переданному номеру телефона
     * @param telephone String
     * @return ReaderEntity
     */
    @GetMapping("/telephone={telephone}")
    public ReaderEntity findReader(@PathVariable String telephone){
        return this.repositoryReader.findReaderEntityByTelephone(telephone);
    }
}
