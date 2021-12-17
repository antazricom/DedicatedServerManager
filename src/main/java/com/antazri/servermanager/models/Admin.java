package com.antazri.servermanager.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated
    @Column(name = "user_role", nullable = false)
    private AdminRole adminRole;

    public Admin() {
    }

    public Admin(int id, String username, String password, AdminRole adminRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.adminRole = adminRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminRole getUserRole() {
        return adminRole;
    }

    public void setUserRole(AdminRole adminRole) {
        this.adminRole = adminRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id && Objects.equals(username, admin.username) && Objects.equals(password, admin.password) && adminRole == admin.adminRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, adminRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + adminRole +
                '}';
    }
}
