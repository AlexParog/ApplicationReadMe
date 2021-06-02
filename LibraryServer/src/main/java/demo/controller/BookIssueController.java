package demo.controller;

import demo.utils.DateUtil;
import demo.entity.ExemplarEntity;
import demo.entity.IssueOfBookEntity;
import demo.repository.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * REST контроллер для взаимодействия с базой данных по выданным книгам
 */
@RestController
@RequestMapping("book_issue/")
public class BookIssueController {

    private final RepositoryIssueOfBook bookIssue;
    private final RepositoryReader repositoryReader;
    private final RepositoryExemplar repositoryExemplar;
    private final RepositoryCoWorkers repositoryCoWorkers;

    /**
     * Инициализатор класса
     *
     * @param bookIssue           RepositoryIssueOfBook
     * @param repositoryReader    RepositoryReader
     * @param repositoryExemplar  RepositoryExemplar
     * @param repositoryCoWorkers RepositoryCoWorkers
     */
    public BookIssueController(RepositoryIssueOfBook bookIssue, RepositoryReader repositoryReader,
                               RepositoryExemplar repositoryExemplar, RepositoryCoWorkers repositoryCoWorkers) {
        this.bookIssue = bookIssue;
        this.repositoryReader = repositoryReader;
        this.repositoryExemplar = repositoryExemplar;
        this.repositoryCoWorkers = repositoryCoWorkers;

    }

    /**
     * Возвращает все найденнные в бд записи по выданным книгам
     *
     * @return String
     */
    @GetMapping("/AllIssues")
    public String getAllIssues() {
        return bookIssue.findAll().toString();
    }

    /**
     * Преобразует переданную строку в IssueOfBookEntity и сохраняет объект в бд
     *
     * @param book_issue String
     * @return IssueOfBookEntity
     */
    @PostMapping("/new_issue")
    public IssueOfBookEntity addNewIssue(@RequestBody String book_issue) {
        List<JSONObject> jsons = toJson(book_issue);
        for (JSONObject jsonObject : jsons) {
            System.out.println(jsonObject);
        }
        IssueOfBookEntity issueOfBookEntity = new IssueOfBookEntity();
        issueOfBookEntity.setInventoryNumber(jsons.get(0).getInt("inventoryNumber"));
        issueOfBookEntity.setReturnDate(DateUtil.parse(jsons.get(0).getString("returnDate")));
        issueOfBookEntity.setLost(jsons.get(0).getBoolean("lost"));
        issueOfBookEntity.setExemplarEntity(this.repositoryExemplar.findExemplarById(jsons.get(3).getLong("id")));
        issueOfBookEntity.setReader(this.repositoryReader.findReaderById(jsons.get(1).getLong("id")));
        issueOfBookEntity.setWorker(this.repositoryCoWorkers.findCoWorkersById(jsons.get(2).getLong("id")));
        return this.bookIssue.save(issueOfBookEntity);
    }

    /**
     * Преобразует переданную строку в IssueOfBookEntity и перезаписывает объект в бд
     *
     * @param book_issue String
     * @return IssueOfBookEntity
     */
    @PutMapping("/update")
    public IssueOfBookEntity editIssue(@RequestBody String book_issue) {
        List<JSONObject> jsons = toJson(book_issue);
        IssueOfBookEntity issueOfBookEntity = new IssueOfBookEntity();
        issueOfBookEntity.setId(jsons.get(0).getLong("id"));
        issueOfBookEntity.setInventoryNumber(jsons.get(0).getInt("inventoryNumber"));
        issueOfBookEntity.setReturnDate(DateUtil.parse(jsons.get(0).getString("returnDate")));
        issueOfBookEntity.setLost(jsons.get(0).getBoolean("lost"));
        issueOfBookEntity.setExemplarEntity(this.repositoryExemplar.findExemplarById(jsons.get(3).getLong("id")));
        issueOfBookEntity.setReader(this.repositoryReader.findReaderById(jsons.get(1).getLong("id")));
        issueOfBookEntity.setWorker(this.repositoryCoWorkers.findCoWorkersById(jsons.get(2).getLong("id")));
        return this.bookIssue.save(issueOfBookEntity);
    }

    /**
     * Возвращает список найденных по части названия экземпляров
     *
     * @param name String
     * @return List of String
     */
    @GetMapping("/findBooks_name={name}")
    public List<String> findBooksByName(@PathVariable String name) {
        name = URLDecoder.decode(name, StandardCharsets.UTF_8);
        System.out.println(name);
        List<ExemplarEntity> exemplarEntities = new ArrayList<>();
        for (ExemplarEntity entity : this.repositoryExemplar.findAll()) {
            if (entity.getAvailabilityInTheLibrary()) {
                if (entity.getBookEntity().getNameOfTheBook().contains(name)) {
                    exemplarEntities.add(entity);
                }
            }
        }
        System.out.println(exemplarEntities);

        List<String> siuted = new ArrayList<>();
        for (ExemplarEntity entity : exemplarEntities) {
            List<IssueOfBookEntity> sortedOnes = this.bookIssue.findAllByExemplarEntity(entity);
            sortedOnes.sort((u1, u2) -> u2.getReturnDate().compareTo(u1.getReturnDate()));
            if (sortedOnes.size() > 0) {
                if (sortedOnes.get(0).getReturnDate().isBefore(LocalDate.now())) {
                    siuted.add(entity.toString());
                }
            } else {
                siuted.add(entity.toString());
            }
        }
        return siuted;
    }

    /**
     * Возвращает список найденных по части фамилии или имени автора экземпляров
     *
     * @param author String
     * @return List of String
     */
    @GetMapping("/findBooks_author={author}")
    public List<String> findBooksByAuthor(@PathVariable String author) {
        author = URLDecoder.decode(author, StandardCharsets.UTF_8);
        List<ExemplarEntity> exemplarEntities = new ArrayList<>();
        for (ExemplarEntity entity : this.repositoryExemplar.findAll()) {
            if (entity.getAvailabilityInTheLibrary()) {
                if (entity.getBookEntity().getAuthor().getFirstName().contains(author) ||
                        entity.getBookEntity().getAuthor().getLastName().contains(author)) {
                    exemplarEntities.add(entity);
                }
            }
        }
        List<String> siuted = new ArrayList<>();
        for (ExemplarEntity entity : exemplarEntities) {
            List<IssueOfBookEntity> sortedOnes = this.bookIssue.findAllByExemplarEntity(entity);
            sortedOnes.sort((u1, u2) -> u2.getReturnDate().compareTo(u1.getReturnDate()));
            if (sortedOnes.size() > 0) {
                if (sortedOnes.get(0).getReturnDate().isBefore(LocalDate.now())) {
                    siuted.add(entity.toString());
                }
            } else {
                siuted.add(entity.toString());
            }
        }
        return siuted;
    }

    /**
     * Собирает соревновательную статистику в JSONObject
     *
     * @return String
     */
    @GetMapping("/statisticsWorkers")
    public String getStatisticalWorkers() {
        JSONObject jsonObject = new JSONObject();
        for (IssueOfBookEntity issueOfBookEntity : this.bookIssue.findAll()) {
            if (jsonObject.keySet().contains(issueOfBookEntity.getWorker().getFirstName() + " " +
                    issueOfBookEntity.getWorker().getLastName() + ";" + issueOfBookEntity.getWorker().getId())) {
                jsonObject.put(issueOfBookEntity.getWorker().getFirstName() + " " +
                                issueOfBookEntity.getWorker().getLastName() + ";" + issueOfBookEntity.getWorker().getId(),
                        jsonObject.getInt(issueOfBookEntity.getWorker().getFirstName() + " " +
                                issueOfBookEntity.getWorker().getLastName() + ";" +
                                issueOfBookEntity.getWorker().getId()) + 1);
            } else {
                jsonObject.put(issueOfBookEntity.getWorker().getFirstName() + " " +
                        issueOfBookEntity.getWorker().getLastName() + ";" + issueOfBookEntity.getWorker().getId(), 1);
            }
        }
        return String.valueOf(jsonObject);
    }

    /**
     * Собирает соревновательную статистику в JSONObject
     *
     * @return String
     */
    @GetMapping("/statisticsYears")
    public String getStatisticalYears() {
        JSONObject jsonObject = new JSONObject();
        for (IssueOfBookEntity issueOfBookEntity : this.bookIssue.findAll()) {
            if (jsonObject.keySet().contains(String.valueOf(issueOfBookEntity.getReturnDate().getYear()))) {
                jsonObject.put(String.valueOf(issueOfBookEntity.getReturnDate().getYear()),
                        jsonObject.getInt(String.valueOf(issueOfBookEntity.getReturnDate().getYear())) + 1);
            } else {
                jsonObject.put(String.valueOf(issueOfBookEntity.getReturnDate().getYear()), 1);
            }
        }
        return String.valueOf(jsonObject);
    }

    /**
     * конвертирует строку в список с JSONObject
     *
     * @param book_issue String
     * @return List of JSONObject
     */
    public List<JSONObject> toJson(String book_issue) {
        List<JSONObject> result = new ArrayList<>();
        JSONObject rawIssue = new JSONObject(book_issue);
        JSONObject rawReader = rawIssue.getJSONObject("reader");
        JSONObject worker = rawIssue.getJSONObject("worker");
        JSONObject exemplar = rawIssue.getJSONObject("exemplarEntity");
        result.add(rawIssue);
        result.add(rawReader);
        result.add(worker);
        result.add(exemplar);
        return result;
    }
}
