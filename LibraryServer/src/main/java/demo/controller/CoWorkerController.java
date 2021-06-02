package demo.controller;

import demo.entity.CoWorkersEntity;
import demo.exception.CoWorkerAlreadyExistException;
import demo.exception.NoSuchCoWorkerException;
import demo.repository.RepositoryCoWorkers;
import demo.repository.RepositoryPosition;
import demo.utils.Filler;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для взаимодействия с базой данных по сотрудникам
 */
@RestController
@RequestMapping("worker/")
public class CoWorkerController {

    private final RepositoryCoWorkers coWorker;
    private final RepositoryPosition position;

    /**
     * Инициализатор класса
     * @param repositoryCoWorkers RepositoryCoWorkers
     * @param position RepositoryPosition
     */
    public CoWorkerController(RepositoryCoWorkers repositoryCoWorkers,
                              RepositoryPosition position){
        this.coWorker = repositoryCoWorkers;
        this.position = position;
    }

    /**
     * Регистрирует сотрудника, если его еще нет в базе. Иначе возвращает нужный ответ от сервера
     * @param strWorker String
     * @return CoWorkersEntity
     * @throws CoWorkerAlreadyExistException если работник уже есть в базе
     */
    @PostMapping("/registration")
    public CoWorkersEntity registration(@RequestBody String strWorker) throws CoWorkerAlreadyExistException {
        CoWorkersEntity worker = Filler.fillWorker(new JSONObject(strWorker));
        if (coWorker.findCoWorkersEntityByLogin(worker.getLogin())==null &&
                coWorker.findCoWorkersEntityByTelephone(worker.getTelephone())==null){
            worker.setPositionEntity(position.findPositionById((long) 1));
            return coWorker.save(worker);
        }else{
            throw new CoWorkerAlreadyExistException("Пользователь с таким именем или телефоном существует");
        }
    }

    /**
     * Проверяет, есть ли переданный сотрудник в базе и может ли он войти в систему с переданным паролем
     * @param login String
     * @param password String
     * @return Boolean
     * @throws NoSuchCoWorkerException если работника не было найдено
     */
    @GetMapping("/signInLogin={login}&Password={password}")
    public Boolean signIn(@PathVariable String login, @PathVariable String password) throws NoSuchCoWorkerException {
        if (coWorker.findCoWorkersEntityByLogin(login) == null) {
            return false;
        }else {
            return (coWorker.findCoWorkersEntityByLogin(login).getPassword().equals(String.valueOf(password.hashCode())));
        }
    }

    /**
     * Возвращает сотрудника по переданному логину
     * @param login String
     * @return String
     */
    @GetMapping("/Login={login}")
    public String findWorker(@PathVariable String login){
        return this.coWorker.findCoWorkersEntityByLogin(login).toString();
    }
}
