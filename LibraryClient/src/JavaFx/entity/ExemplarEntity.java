package JavaFx.entity;

/**
 * Сущность экземпляра
 */
public class ExemplarEntity {
    private Long id;
    private Integer inventoryNumber;
    private Boolean availabilityInTheLibrary;
    private BookEntity bookEntity;

    /**
     * инициализатор класса
     * @param inventoryNumber значение инфентарного номера
     * @param availabilityInTheLibrary доступность в библиотеке
     */
    public ExemplarEntity(Integer inventoryNumber, Boolean availabilityInTheLibrary) {
        this.inventoryNumber = inventoryNumber;
        this.availabilityInTheLibrary = availabilityInTheLibrary;
    }

    /**
     * Пустой инициализатор класса
     */
    public ExemplarEntity(){
    }

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра inventoryNumber
     * @return Integer
     */
    public Integer getInventoryNumber() {
        return inventoryNumber;
    }

    /**
     * возвращает значение параметра availabilityInTheLibrary
     * @return Boolean
     */
    public Boolean getAvailabilityInTheLibrary() {
        return availabilityInTheLibrary;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру inventoryNumber
     * @param inventoryNumber Integer
     */
    public void setInventoryNumber(Integer inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    /**
     * устанавливает значение параметру availabilityInTheLibrary
     * @param availabilityInTheLibrary Boolean
     */
    public void setAvailabilityInTheLibrary(Boolean availabilityInTheLibrary) {
        this.availabilityInTheLibrary = availabilityInTheLibrary;
    }

    /**
     * возвращает значение параметра bookEntity
     * @return BookEntity
     */
    public BookEntity getBookEntity() {
        return bookEntity;
    }

    /**
     * устанавливает значение параметру bookEntity
     * @param bookEntity BookEntity
     */
    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    /**
     * Конвертирует объект в строку
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", inventoryNumber=" + inventoryNumber +
                ", availabilityInTheLibrary=" + availabilityInTheLibrary +
                ", bookEntity=" + bookEntity +
                '}';
    }
}
