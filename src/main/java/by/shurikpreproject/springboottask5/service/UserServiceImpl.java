package by.shurikpreproject.springboottask5.service;

import by.shurikpreproject.springboottask5.dao.UserDao;
import by.shurikpreproject.springboottask5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional//не надо вызывать типовой код try entityManager / .begin();/.commit();/catch .rollback();
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }

    @Override
    public List findListByUsername(String filter) {
        return userDao.findListByUsername(filter);
    }
}
