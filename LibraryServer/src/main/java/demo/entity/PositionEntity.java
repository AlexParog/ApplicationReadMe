package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Сущность должности
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "position")
public class PositionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", length = 40, nullable = false)
    private String jobTitle;

    /**
     * инициализатор класса
     * @param jobTitle String название должности
     */
    public PositionEntity(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Пустой инициализатор класса
     */
    public PositionEntity(){}

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра jobTitle
     * @return String
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру jobTitle
     * @param jobTitle String
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
