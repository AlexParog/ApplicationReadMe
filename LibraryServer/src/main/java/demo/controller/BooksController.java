package demo.controller;

import demo.utils.Filler;
import demo.entity.BookEntity;
import demo.entity.ExemplarEntity;
import demo.entity.IssueOfBookEntity;
import demo.repository.*;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST контроллер для взаимодействия с базой данных по книгам
 */
@RestController
@RequestMapping("books/")
public class BooksController {
    private final RepositoryBook repositoryBook;
    private final RepositoryAuthor repositoryAuthor;
    private final RepositoryEditionType repositoryEditionType;
    private final RepositoryPublishingHouse repositoryPublishingHouse;
    private final RepositoryExemplar repositoryExemplar;
    private final RepositoryIssueOfBook repositoryIssueOfBook;

    /**
     * Инициализатор класса
     * @param repositoryBook RepositoryBook
     * @param repositoryAuthor RepositoryAuthor
     * @param repositoryEditionType RepositoryEditionType
     * @param repositoryPublishingHouse RepositoryPublishingHouse
     * @param repositoryExemplar RepositoryExemplar
     * @param repositoryIssueOfBook RepositoryIssueOfBook
     */
    public BooksController(RepositoryBook repositoryBook, RepositoryAuthor repositoryAuthor,
                           RepositoryEditionType repositoryEditionType, RepositoryPublishingHouse repositoryPublishingHouse,
                           RepositoryExemplar repositoryExemplar, RepositoryIssueOfBook repositoryIssueOfBook){
        this.repositoryBook = repositoryBook;
        this.repositoryAuthor = repositoryAuthor;
        this.repositoryEditionType = repositoryEditionType;
        this.repositoryPublishingHouse = repositoryPublishingHouse;
        this.repositoryExemplar = repositoryExemplar;
        this.repositoryIssueOfBook = repositoryIssueOfBook;
    }

    /**
     * Возвращает все найденнные в бд записи по книгам
     * @return String
     */
    @GetMapping("/AllBooks")
    public List<BookEntity> getAllBooks(){
        return repositoryBook.findAll();
    }

    /**
     * Находит книгу по переданному id и удаляет ее вместе со всем записями,
     * в которых данный объект являлся внешним ключем
     * @param id Long
     */
    @Transactional
    @DeleteMapping("/id={id}")
    public void deleteBook(@PathVariable Long id){
        List<IssueOfBookEntity> issues = new ArrayList<>();
        List<ExemplarEntity> exemplars= this.repositoryExemplar.findAllByBookEntity(this.repositoryBook.findBooksById(id));
        for (ExemplarEntity exemplarEntity:exemplars){
            issues.addAll(this.repositoryIssueOfBook.findAllByExemplarEntity(exemplarEntity));
        }
        for (IssueOfBookEntity entity: issues){
            this.repositoryIssueOfBook.delete(entity);
        }
        for (ExemplarEntity entity: exemplars){
            this.repositoryExemplar.delete(entity);
        }
        this.repositoryBook.delete(this.repositoryBook.findBooksById(id));
    }

    /**
     * Преобразует переданную строку в BookEntity и сохраняет объект в бд
     * @param line String
     */
    @PostMapping("/addBook")
    void createBook(@RequestBody String line) {
        System.out.println(line);
        JSONObject jsonObject = new JSONObject(line);
        BookEntity bookEntity = Filler.fillBook(jsonObject);
        this.repositoryBook.save(bookEntity);
    }

    /**
     * Преобразует переданную строку в BookEntity и перезаписывает объект в бд
     * @param line String
     */
    @Transactional
    @PutMapping("/update")
    public void update(@RequestBody String line){
        JSONObject jsonObject = new JSONObject(line);
        BookEntity bookEntity = Filler.fillBook(jsonObject);
        this.repositoryBook.save(bookEntity);
    }
}
