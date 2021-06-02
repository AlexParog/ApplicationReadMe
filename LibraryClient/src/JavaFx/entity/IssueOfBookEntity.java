package JavaFx.entity;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Сущность выдачи книг
 */
public class IssueOfBookEntity {
    private Long id;
    private Integer inventoryNumber;
    private LocalDate returnDate;
    private Boolean lost;
    private ReaderEntity reader;
    private ExemplarEntity exemplarEntity;
    private CoWorkersEntity worker;
    
    /**
     * инициализатор класса
     * @param inventoryNumber значение инвентарного номера
     * @param returnDate дата возврата
     * @param lost потеряня ли книга
     */
    public IssueOfBookEntity(Integer inventoryNumber, LocalDate returnDate, Boolean lost) {
        this.inventoryNumber = inventoryNumber;
        this.returnDate = returnDate;
        this.lost = lost;
    }

    /**
     * Пустой инициализатор класса
     */
    public IssueOfBookEntity(){}

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
     * возвращает значение параметра returnDate
     * @return LocalDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * возвращает значение параметра lost
     * @return Boolean
     */
    public Boolean getLost() {
        return lost;
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
     * устанавливает значение параметру returnDate
     * @param returnDate LocalDate
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * устанавливает значение параметру lost
     * @param lost Boolean
     */
    public void setLost(Boolean lost) {
        this.lost = lost;
    }

    /**
     * Возвращает SimpleStringProperty для параметра названия книги
     * @return SimpleStringProperty
     */
    public SimpleStringProperty getBookNameProperty(){
        return new SimpleStringProperty(this.getExemplarEntity().getBookEntity().getNameOfTheBook());
    }

    /**
     * Возвращает SimpleStringProperty для параметра атвора
     * @return SimpleStringProperty
     */
    public SimpleStringProperty getClientNameProperty(){
        return new SimpleStringProperty(this.getReader().getFirstName()+" "+this.getReader().getLastName());
    }
    

    /**
     * возвращает значение параметра reader
     * @return ReaderEntity
     */
    public ReaderEntity getReader() {
        return reader;
    }

    /**
     * устанавливает значение параметру reader
     * @param reader ReaderEntity
     */
    public void setReader(ReaderEntity reader) {
        this.reader = reader;
    }
    

    /**
     * возвращает значение параметра worker
     * @return CoWorkersEntity
     */
    public CoWorkersEntity getWorker() {
        return worker;
    }

    /**
     * устанавливает значение параметру worker
     * @param worker CoWorkersEntity
     */
    public void setWorker(CoWorkersEntity worker) {
        this.worker = worker;
    }
    

    /**
     * возвращает значение параметра exemplarEntity
     * @return ExemplarEntity
     */
    public ExemplarEntity getExemplarEntity() {
        return exemplarEntity;
    }

    /**
     * устанавливает значение параметру exemplarEntity
     * @param exemplarEntity ExemplarEntity
     */
    public void setExemplarEntity(ExemplarEntity exemplarEntity) {
        this.exemplarEntity = exemplarEntity;
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
                ", returnDate=" + returnDate +
                ", lost=" + lost +
                ", reader=" + reader +
                ", worker=" + worker +
                ", exemplarEntity=" + exemplarEntity +
                '}';
    }
}
