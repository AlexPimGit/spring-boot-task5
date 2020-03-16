package by.shurikpreproject.springboottask5.dao;

import by.shurikpreproject.springboottask5.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    //делаем по правилам имя метода https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    List<User> findByUserName(String name);
}
