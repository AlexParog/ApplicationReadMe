package demo.controller;

import demo.entity.AuthorEntity;
import demo.entity.BookEntity;
import demo.repository.RepositoryAuthor;
import demo.repository.RepositoryBook;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import demo.utils.Filler;

import java.util.List;

/**
 * REST контроллер для взаимодействия с базой данных по авторам
 */
@RestController
@RequestMapping("authors/")
public class AuthorsController {
    private final RepositoryAuthor repositoryAuthor;
    private final RepositoryBook repositoryBook;

    /**
     * Инициализатор класса
     * @param repositoryAuthor RepositoryAuthor
     * @param repositoryBook RepositoryBook
     */
    public AuthorsController(RepositoryAuthor repositoryAuthor, RepositoryBook repositoryBook){
        this.repositoryAuthor = repositoryAuthor;
        this.repositoryBook = repositoryBook;
    }

    /**
     * Преобразует переданную строку в AuthorEntity и сохраняет объект в бд
     * @param line String
     */
    @PostMapping("/addAuthor")
    void createAuthor(@RequestBody String line) {
        JSONObject jsonObject = new JSONObject(line);
        AuthorEntity authorEntity = Filler.fillAuthor(jsonObject);
        this.repositoryAuthor.save(authorEntity);
    }

    /**
     * Возвращает все найденнные в бд записи по авторам
     * @return String
     */
    @GetMapping("/AllAuthors")
    public List<AuthorEntity> getAllAuthors(){
        return repositoryAuthor.findAll();
    }

    /**
     * Находит автора по переданному id и удаляет его вместе со всем записями,
     * в которых данный объект являлся внешним ключем
     * @param id Long
     */
    @Transactional
    @DeleteMapping("/id={id}")
    public void deleteAuthor(@PathVariable Long id){
        List<BookEntity> books = this.repositoryBook.findAllByAuthor(this.repositoryAuthor.findAuthorById(id));
        for (BookEntity book: books){
            this.repositoryBook.delete(book);
        }
        this.repositoryAuthor.deleteById(id);
    }

    /**
     * Преобразует переданную строку в AuthorEntity и перезаписывает объект в бд
     * @param line String
     */
    @Transactional
    @PutMapping("/update")
    public void update(@RequestBody String line){
        JSONObject jsonObject = new JSONObject(line);
        AuthorEntity authorEntity = Filler.fillAuthor(jsonObject);
        this.repositoryAuthor.save(authorEntity);
    }
}
