package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность книг
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_the_book", length = 60, nullable = false)
    private String nameOfTheBook;

    @Column(name = "year_of_issue", nullable = false)
    private LocalDate yearOfIssue;

    /**
     * Инициализатор класса
     * @param nameOfTheBook название книги
     * @param yearOfIssue год выпуска
     */
    public BookEntity(String nameOfTheBook, LocalDate yearOfIssue) {
        this.nameOfTheBook = nameOfTheBook;
        this.yearOfIssue = yearOfIssue;
    }

    /**
     * Пустой инициализатор класса
     */
    public BookEntity(){}

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра nameOfTheBook
     * @return String
     */
    public String getNameOfTheBook() {
        return nameOfTheBook;
    }

    /**
     * возвращает значение параметра yearOfIssue
     * @return LocalDate
     */
    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру nameOfTheBook
     * @param nameOfTheBook String
     */
    public void setNameOfTheBook(String nameOfTheBook) {
        this.nameOfTheBook = nameOfTheBook;
    }

    /**
     * устанавливает значение параметру yearOfIssue
     * @param yearOfIssue LocalDate
     */
    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private AuthorEntity author;

    /**
     * возвращает значение параметра author
     * @return AuthorEntity
     */
    public AuthorEntity getAuthor() {
        return author;
    }

    /**
     * устанавливает значение параметру author
     * @param author AuthorEntity
     */
    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishing_house")
    private PublishingHouseEntity publishingHouse;

    /**
     * возвращает значение параметра publishingHouse
     * @return PublishingHouseEntity
     */
    public PublishingHouseEntity getPublishingHouse() {
        return publishingHouse;
    }

    /**
     * устанавливает значение параметру publishingHouse
     * @param publishingHouse PublishingHouseEntity
     */
    public void setPublishingHouse(PublishingHouseEntity publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_type")
    private EditionTypeEntity editionType;

    /**
     * возвращает значение параметра editionType
     * @return EditionTypeEntity
     */
    public EditionTypeEntity getEditionType() {
        return editionType;
    }

    /**
     * устанавливает значение параметру editionType
     * @param editionType EditionTypeEntity
     */
    public void setEditionType(EditionTypeEntity editionType) {
        this.editionType = editionType;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nameOfTheBook='" + nameOfTheBook + '\'' +
                ", yearOfIssue=" + yearOfIssue +
                ", author=" + author +
                ", publishingHouse=" + publishingHouse +
                ", editionType=" + editionType +
                '}';
    }
}
