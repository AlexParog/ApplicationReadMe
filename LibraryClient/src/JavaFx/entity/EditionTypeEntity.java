package JavaFx.entity;

/**
 * Сущность типа публикации
 */
public class EditionTypeEntity {
    private Long id;
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
