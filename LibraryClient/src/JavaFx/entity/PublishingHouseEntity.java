package JavaFx.entity;

/**
 * Сущность издательства
 */
public class PublishingHouseEntity {
    private Long id;
    private String name;
    private String city;

    /**
     * инициализатор класса
     * @param name String
     * @param city String
     */
    public PublishingHouseEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }

    /**
     * Пустой инициализатор класса
     */
    public PublishingHouseEntity(){}

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра city
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * возвращает значение параметра name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * устанавливает значение параметру city
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
