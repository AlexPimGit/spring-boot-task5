package by.shurikpreproject.springboottask5.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@NotBlank(message = "Email is mandatory")
    //@ NotBlank Это подразумевает, что мы можем использовать Hibernate Validator для
    // проверки ограниченных полей перед сохранением или обновлением объекта в базе данных.
    private String userName;
    //@NotBlank(message = "Email is mandatory")
    private String email;
    @ElementCollection (targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable (name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated (EnumType.STRING)
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(String name, String email) {
        this.userName = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
