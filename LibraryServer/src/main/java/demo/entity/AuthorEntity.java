package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность авторов книг
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "author")
public class AuthorEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "description", length = 128)
    private String description;

    /**
     * Инициализатор класса
     * @param firstName Имя, String
     * @param lastName Фамилия, String
     * @param birthday Дата рождения, LocalDate
     * @param description Детали, String
     */
    public AuthorEntity(String firstName, String lastName, LocalDate birthday, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.description = description;
    }

    /**
     * Пустой инициализатор класса
     */
    public AuthorEntity() { }

    /**
     * Возвращает значение атрибута id
     * @return id Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает значение атрибута description
     * @return description String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает значение атрибута firstName
     * @return firstName String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Возвращает значение атрибута lastName
     * @return lastName String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Возвращает значение атрибута birthday
     * @return birthday LocalDate
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Устанавливает значение параметру firstName
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Устанавливает значение параметру lastName
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Устанавливает значение параметру birthday
     * @param birthday LocalDate
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Устанавливает значение параметру description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", description='" + description + '\'' +
                '}';
    }
}
