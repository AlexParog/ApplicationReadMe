package demo.utils;

import demo.entity.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Класс-утилита для преобразования json объектов в сущности
 */
public class Filler {
    /**
     * Инициализатор класса
     */
    public Filler(){}

    /**
     * Преобразует JSONObject в сущность выданной книги
     * @param rawIssue JSONObject
     * @return IssueOfBookEntity
     */
    public static IssueOfBookEntity fillIssue(JSONObject rawIssue){
        IssueOfBookEntity issueOfBookEntity = new IssueOfBookEntity();
        try {
            issueOfBookEntity.setId(rawIssue.getLong("id"));
            issueOfBookEntity.setInventoryNumber(rawIssue.getInt("inventoryNumber"));
            issueOfBookEntity.setLost(rawIssue.getBoolean("lost"));
            issueOfBookEntity.setReturnDate(DateUtil.parse(rawIssue.getString("returnDate")));
            issueOfBookEntity.setWorker(fillWorker(rawIssue.getJSONObject("worker")));
            issueOfBookEntity.setExemplarEntity(fillExemplar(rawIssue.getJSONObject("exemplarEntity")));
            issueOfBookEntity.setReader(fillReader(rawIssue.getJSONObject("reader")));
            return issueOfBookEntity;
        }catch (Exception e){
            issueOfBookEntity.setInventoryNumber(rawIssue.getInt("inventoryNumber"));
            issueOfBookEntity.setLost(rawIssue.getBoolean("lost"));
            issueOfBookEntity.setReturnDate(DateUtil.parse(rawIssue.getString("returnDate")));
            issueOfBookEntity.setWorker(fillWorker(rawIssue.getJSONObject("worker")));
            issueOfBookEntity.setExemplarEntity(fillExemplar(rawIssue.getJSONObject("exemplarEntity")));
            issueOfBookEntity.setReader(fillReader(rawIssue.getJSONObject("reader")));
            return issueOfBookEntity;
        }
    }

    /**
     * Преобразует JSONObject в сущность должности
     * @param rawPosition JSONObject
     * @return PositionEntity
     */
    public static PositionEntity fillPosition(JSONObject rawPosition){
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(rawPosition.getLong("id"));
        positionEntity.setJobTitle(rawPosition.getString("jobTitle"));
        return positionEntity;
    }

    /**
     * Преобразует JSONObject в сущность экземпляра
     * @param rawExemplar JSONObject
     * @return ExemplarEntity
     */
    public static ExemplarEntity fillExemplar(JSONObject rawExemplar){
        ExemplarEntity exemplarEntity = new ExemplarEntity();
        exemplarEntity.setId(rawExemplar.getLong("id"));
        exemplarEntity.setInventoryNumber(rawExemplar.getInt("inventoryNumber"));
        exemplarEntity.setAvailabilityInTheLibrary(rawExemplar
                .getBoolean("availabilityInTheLibrary"));
        exemplarEntity.setBookEntity(fillBook(rawExemplar.getJSONObject("bookEntity")));
        return exemplarEntity;
    }

    /**
     * Преобразует JSONObject в сущность читателя
     * @param rawReader JSONObject
     * @return ReaderEntity
     */
    public static ReaderEntity fillReader(JSONObject rawReader){
        ReaderEntity readerEntity = new ReaderEntity();
        readerEntity.setId(rawReader.getLong("id"));
        readerEntity.setFirstName(rawReader.getString("firstName"));
        readerEntity.setLastName(rawReader.getString("lastName"));
        readerEntity.setTelephone(rawReader.getString("telephone"));
        readerEntity.setAddress(rawReader.getString("address"));
        return readerEntity;
    }

    /**
     * Преобразует JSONObject в сущность автора
     * @param rawAuthor JSONObject
     * @return AuthorEntity
     */
    public static AuthorEntity fillAuthor(JSONObject rawAuthor){
        AuthorEntity authorEntity = new AuthorEntity();
        try {
            authorEntity.setId(rawAuthor.getLong("id"));
            authorEntity.setFirstName(rawAuthor.getString("firstName"));
            authorEntity.setLastName(rawAuthor.getString("lastName"));
            authorEntity.setBirthday(DateUtil.parse(rawAuthor.getString("birthday")));
            authorEntity.setDescription(rawAuthor.getString("description"));
            System.out.println(authorEntity.toString());
            return authorEntity;
        }catch (Exception e){
            authorEntity.setFirstName(rawAuthor.getString("firstName"));
            authorEntity.setLastName(rawAuthor.getString("lastName"));
            authorEntity.setBirthday(DateUtil.parse(rawAuthor.getString("birthday")));
            authorEntity.setDescription(rawAuthor.getString("description"));
            return authorEntity;
        }
    }

    /**
     * Преобразует JSONObject в сущность издательства
     * @param rawHouse JSONObject
     * @return PublishingHouseEntity
     */
    public static PublishingHouseEntity fillHouse(JSONObject rawHouse){
        PublishingHouseEntity publishingHouseEntity = new PublishingHouseEntity();
        publishingHouseEntity.setId(rawHouse.getLong("id"));
        publishingHouseEntity.setName(rawHouse.getString("name"));
        publishingHouseEntity.setCity(rawHouse.getString("city"));
        return publishingHouseEntity;
    }

    /**
     * Преобразует JSONObject в сущность тип публикации
     * @param rawEdition JSONObject
     * @return EditionTypeEntity
     */
    public static EditionTypeEntity fillEdition(JSONObject rawEdition){
        EditionTypeEntity editionTypeEntity = new EditionTypeEntity();
        editionTypeEntity.setId(rawEdition.getLong("id"));
        editionTypeEntity.setTypeName(rawEdition.getString("typeName"));
        return editionTypeEntity;
    }

    /**
     * Преобразует JSONObject в сущность книги
     * @param rawBook JSONObject
     * @return BookEntity
     */
    public static BookEntity fillBook(JSONObject rawBook){
        BookEntity bookEntity = new BookEntity();
        try {
            bookEntity.setId(rawBook.getLong("id"));
            bookEntity.setNameOfTheBook(rawBook.getString("nameOfTheBook"));
            bookEntity.setYearOfIssue(DateUtil.parse(rawBook.getString("yearOfIssue")));
            bookEntity.setAuthor(fillAuthor(rawBook.getJSONObject("author")));
            bookEntity.setEditionType(fillEdition(rawBook.getJSONObject("editionType")));
            bookEntity.setPublishingHouse(fillHouse(rawBook.getJSONObject("publishingHouse")));
            return bookEntity;
        }catch (Exception e){
            bookEntity.setNameOfTheBook(rawBook.getString("nameOfTheBook"));
            bookEntity.setYearOfIssue(DateUtil.parse(rawBook.getString("yearOfIssue")));
            bookEntity.setAuthor(fillAuthor(rawBook.getJSONObject("author")));
            bookEntity.setEditionType(fillEdition(rawBook.getJSONObject("editionType")));
            bookEntity.setPublishingHouse(fillHouse(rawBook.getJSONObject("publishingHouse")));
            return bookEntity;
        }
    }

    /**
     * Преобразует JSONObject в сущность сотрудника
     * @param rawWorker JSONObject
     * @return CoWorkersEntity
     */
    public static CoWorkersEntity fillWorker(JSONObject rawWorker){
        CoWorkersEntity editionTypeEntity = new CoWorkersEntity();
        try{
            editionTypeEntity.setId(rawWorker.getLong("id"));
            editionTypeEntity.setPositionEntity(fillPosition(rawWorker.getJSONObject("positionEntity")));
        }catch (JSONException e){
            System.out.println();
        }
        editionTypeEntity.setCity(rawWorker.getString("city"));
        editionTypeEntity.setFirstName(rawWorker.getString("firstName"));
        editionTypeEntity.setLastName(rawWorker.getString("lastName"));
        editionTypeEntity.setLogin(rawWorker.getString("login"));
        editionTypeEntity.setTelephone(rawWorker.getString("telephone"));
        editionTypeEntity.setPassword(String.valueOf(rawWorker.getString("password").hashCode()));
        return editionTypeEntity;
    }
}
