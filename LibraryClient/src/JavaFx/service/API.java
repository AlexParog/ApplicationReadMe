package JavaFx.service;

import JavaFx.exception.NoConnectionException;
import JavaFx.utils.DateUtil;
import JavaFx.utils.HTTP;
import JavaFx.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Класс со статическими методами для выполнения формирования запросов и
 * возвращения результатов обращения к серверу
 */
public class API {

    /**
     * Возвращает значение начала строки для формирования ссылки
     * @return String
     */
    public static String localHost() {
        return "http://localhost:8080/";
    }

    /**
     * Формирует запрос на регистрацию человека и обрабатывает результат выполнения
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean sighUp(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "worker/registration";
        String request;
        try {
            request = HTTP.Post(url, jsonObject);
        }catch (IOException e){
            return false;
        }
        if (request != null) {
            try {
                CoWorkersEntity worker = fillWorker(new JSONObject(request));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            throw new NoConnectionException("Нет соединения");
        }

    }

    /**
     * Формирует запрос на вход по логину и паролю и обрабатывает результат выполнения
     * @param login String
     * @param password String
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean signIn(String login, String password) throws NoConnectionException {
        String url = localHost() + "worker/signInLogin=" + login + "&Password=" + password;
        String request = HTTP.GetRequest(url);
        if (request != null) {
            return request.equals("true");
        }else{
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * Формирует запрос на получение всех выданных книг и обрабатывает результат выполнения
     * @return ObservableList из IssueOfBookEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<IssueOfBookEntity> getAllIssues() throws NoConnectionException{
        String url = localHost()+"book_issue/AllIssues";
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request.replace("=", ":"));
        ObservableList<IssueOfBookEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillIssue(rawResult.getJSONObject(i)));
            }catch (org.json.JSONException e){
                e.printStackTrace();
                System.out.println("");
            }
        }
        return result;
    }

    /**
     * Формирует запрос на получение всех книг и обрабатывает результат выполнения
     * @return ObservableList из BookEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<BookEntity> getAllBooks() throws NoConnectionException{
        String url = localHost()+"books/AllBooks";
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        ObservableList<BookEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillBook(rawResult.getJSONObject(i)));
            }catch (org.json.JSONException r){
                System.out.println("");
            }
        }
        return result;
    }

    /**
     * Формирует запрос на получение всех авторов и обрабатывает результат выполнения
     * @return ObservableList из AuthorEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<AuthorEntity> getAllAuthors() throws NoConnectionException{
        String url = localHost()+"authors/AllAuthors";
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        ObservableList<AuthorEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillAuthor(rawResult.getJSONObject(i)));
            }catch (org.json.JSONException e){
                System.out.println("");
            }
        }
        return result;
    }

    /**
     * Формирует запрос на получение всех типов изданий и обрабатывает результат выполнения
     * @return ObservableList из EditionTypeEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<EditionTypeEntity> getAllEditions() throws NoConnectionException{
        String url = localHost()+"editions/AllEditions";
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        ObservableList<EditionTypeEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillEdition(rawResult.getJSONObject(i)));
            }catch (org.json.JSONException e){
                System.out.println("");
            }
        }
        return result;
    }

    /**
     * Формирует запрос на получение всех издательств и обрабатывает результат выполнения
     * @return ObservableList из PublishingHouseEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<PublishingHouseEntity> getAllHouses() throws NoConnectionException{
        String url = localHost()+"publishing_houses/AllPublishingHouses";
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        ObservableList<PublishingHouseEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillHouse(rawResult.getJSONObject(i)));
            }catch (org.json.JSONException e){
                System.out.println("");
            }
        }
        return result;
    }

    /**
     * Формирует запрос на добавление автора в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean addAuthor(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "authors/addAuthor";
        try {
            String request = HTTP.Post(url, jsonObject);
            return !request.equals("");
        }catch (IOException e){
            return false;
        }
    }

    /**
     * Формирует запрос на обновление автора в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean updateAuthor(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "authors/update";
        String request = HTTP.PutRequest(url, String.valueOf(jsonObject));
        return !request.equals("");
    }

    /**
     * Формирует запрос на удаление автора в бд и обрабатывает результат
     * @param id Long
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static void deleteAuthor(Long id) throws NoConnectionException{
        String url = localHost() + "authors/id="+id;
        boolean request = HTTP.DeleteRequest(url);
    }

    /**
     * Формирует запрос на добавление книги в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean addBook(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "books/addBook";
        try {
            String request = HTTP.Post(url, jsonObject);
            return !request.equals("");
        }catch (IOException e){
            return false;
        }
    }

    /**
     * Формирует запрос на изменение книги в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean updateBook(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "books/update";
        String request = HTTP.PutRequest(url, String.valueOf(jsonObject));
        return !request.equals("");
    }

    /**
     * Формирует запрос на удаления книги в бд и обрабатывает результат
     * @param id Long
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static void deleteBook(Long id) throws NoConnectionException{
        String url = localHost() + "books/id="+id;
        boolean request = HTTP.DeleteRequest(url);
    }

    /**
     * Формирует запрос на поиск книги по части названия и обрабатывает результат
     * @param name String
     * @return ObservableList из ExemplarEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<ExemplarEntity> findBookByName(String name) throws NoConnectionException{
        String url = localHost() + "book_issue/findBooks_name="+
                URLEncoder.encode(name, StandardCharsets.UTF_8);
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        ObservableList<ExemplarEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillExemplar(new JSONObject(rawResult.getString(i)
                        .replace("=",":"))));
            }catch (org.json.JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Формирует запрос на поиск книги по части имени или фамилии автора
     * и обрабатывает результат
     * @param author String
     * @return ObservableList из ExemplarEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ObservableList<ExemplarEntity> findBookByAuthor(String author)
            throws NoConnectionException{
        String url = localHost() + "book_issue/findBooks_author="+
                URLEncoder.encode(author, StandardCharsets.UTF_8);
        String request = HTTP.GetRequest(url);
        JSONArray rawResult = new JSONArray(request);
        System.out.println(rawResult);
        ObservableList<ExemplarEntity> result = FXCollections.observableArrayList();
        for(int i=0; i<rawResult.length(); i++){
            try {
                result.add(fillExemplar(new JSONObject(rawResult.getString(i)
                        .replace("=",":"))));
            }catch (org.json.JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Формирует запрос на выдачу книги в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean addIssue(JSONObject jsonObject) throws NoConnectionException {
        String url = localHost() + "book_issue/new_issue";
        try {
            String request = HTTP.Post(url, jsonObject);
            return !request.equals("");
        }catch (IOException e){
            return false;
        }
    }

    /**
     * Формирует запрос на принятие книги в бд и обрабатывает результат
     * @param jsonObject JSONObject
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean updateIssue(JSONObject jsonObject) throws NoConnectionException{
        String url = localHost() + "book_issue/update";
        String request = HTTP.PutRequest(url, String.valueOf(jsonObject));
        return !request.equals("");
    }

    /**
     * Формирует запрос поиск читателя по номеру телефона в бд и обрабатывает результат
     * @param telephone String
     * @return ReaderEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static ReaderEntity findReader(String telephone) throws NoConnectionException{
        String url = localHost() + "reader/telephone="+telephone;
        String request = HTTP.GetRequest(url);
        if (!request.equals("")){
            JSONObject rawResult = new JSONObject(request);
            return fillReader(rawResult);
        }else{
            return null;
        }
    }

    /**
     * Формирует запрос поиск сотрудника по логину в бд и обрабатывает результат
     * @param login String
     * @return CoWorkersEntity
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static CoWorkersEntity findWorker(String login) throws NoConnectionException{
        String url = localHost() + "worker/Login="+login;
        String request = HTTP.GetRequest(url);
        System.out.println(request);
        if (!request.equals("")){
            JSONObject rawResult = new JSONObject(request.replace("=", ":"));
            return fillWorker(rawResult);
        }else{
            return null;
        }
    }

    /**
     * Формирует запрос на получение годовой статистики и обрабатывает результат
     * @return JSONObject
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static JSONObject getYearStatistics() throws NoConnectionException{
        String url = localHost() + "book_issue/statisticsYears";
        String request = HTTP.GetRequest(url);
        if (!request.equals("")){
            JSONObject rawResult = new JSONObject(request.replace("=", ":"));
            return rawResult;
        }else{
            return null;
        }
    }

    /**
     * Формирует запрос на получение соревновательной статистики и обрабатывает результат
     * @return JSONObject
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static JSONObject getWorkerStatistics() throws NoConnectionException{
        String url = localHost() + "book_issue/statisticsWorkers";
        String request = HTTP.GetRequest(url);
        if (!request.equals("")){
            JSONObject rawResult = new JSONObject(request.replace("=", ":"));
            return rawResult;
        }else{
            return null;
        }
    }

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
