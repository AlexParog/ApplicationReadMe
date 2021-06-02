package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Сущность типа публикации
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "edition_type")
public class EditionTypeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", length = 40, nullable = false)
    private String typeName;

    /**
     * инициализатор класса
     * @param typeName String название типа публикации
     */
    public EditionTypeEntity(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Пустой инициализатор класса
     */
    public  EditionTypeEntity(){}

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра typeName
     * @return String
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру typeName
     * @param typeName String
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
