package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        }
)
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(unique = true, name = "username")
    // @UniqueElements(message = "{UniqueElements.username}")
    @Pattern(regexp = "[A-zA-я]+", message = "Имя может содержать только буквы")
    @Size(min = 2, max = 30, message = "Не менее 2 символов и не более 30")
    private String username;
    @Pattern(regexp = "[A-zA-я]+", message = "Фамилия может содержать только буквы")
    @Size(min = 2, max = 30, message = "В фамилии не менее 2 символов и не более 30")
    private String surname;
    @Min(value = 0, message = "Введите корректный возраст")
    private int age;
    @Size(min = 2, message = "Не меньше 2 символов")
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role"))
    private List<Role> roles;

    public User() {
    }

    public User(String username, String surname, int age, String password, List<Role> roles) {
        this.username = username;
        this.surname = surname;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, int age, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public List<Role> getAuthorities() {
        return roles.stream()
                .map(role -> new Role(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", roles=" + roles +
                '}';
    }
}
