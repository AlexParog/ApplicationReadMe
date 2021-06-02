package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Сущность читателя
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "reader")
public class ReaderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "telephone", length = 25, nullable = false)
    private String telephone;

    @Column(name = "address", length = 128, nullable = false)
    private String address;

    /**
     * инициализатор класса
     * @param firstName String
     * @param lastName String
     * @param telephone String
     * @param address String
     */
    public ReaderEntity(String firstName, String lastName, String telephone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.address = address;
    }

    /**
     * Пустой инициализатор класса
     */
    public ReaderEntity(){}

    /**
     * возвращает значение параметра id
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * возвращает значение параметра firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * возвращает значение параметра lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * возвращает значение параметра telephone
     * @return String
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * возвращает значение параметра address
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * устанавливает значение параметру id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * устанавливает значение параметру firstName
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * устанавливает значение параметру lastName
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * устанавливает значение параметру address
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * устанавливает значение параметру telephone
     * @param telephone String
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
