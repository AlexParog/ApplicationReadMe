package JavaFx.entity;

/**
 * Сущность работника
 */
public class CoWorkersEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephone;
    private String city;
    private String login;
    private String password;
    private PositionEntity positionEntity;

    /**
     * Инициализатор класса
     * @param firstName String имя
     * @param lastName String Фамилия
     * @param telephone String номер телефона
     * @param city String город проживания
     */
    public CoWorkersEntity(String firstName, String lastName, String telephone, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.city = city;
    }

    /**
     * Пустой инициализатор класса
     */
    public CoWorkersEntity() {

    }

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
     * возвращает значение параметра city
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * возвращает значение параметра login
     * @return String
     */
    public String getLogin() {
        return login;
    }

    /**
     * возвращает значение параметра password
     * @return String
     */
    public String getPassword() {
        return password;
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
     * устанавливает значение параметру telephone
     * @param telephone String
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * устанавливает значение параметру city
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * устанавливает значение параметру login
     * @param login String
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * устанавливает значение параметру password
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * возвращает значение параметра positionEntity
     * @return PositionEntity
     */
    public PositionEntity getPositionEntity() {
        return positionEntity;
    }

    /**
     * устанавливает значение параметру positionEntity
     * @param positionEntity PositionEntity
     */
    public void setPositionEntity(PositionEntity positionEntity) {
        this.positionEntity = positionEntity;
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
                ", city='" + city + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", positionEntity=" + positionEntity +
                '}';
    }
}
