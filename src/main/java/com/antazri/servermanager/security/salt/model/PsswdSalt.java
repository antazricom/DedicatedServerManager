package com.antazri.servermanager.security.salt.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;

@Document(collection = "salt")
public class PsswdSalt {

    @Id
    private String id;

    private int userId;
    private String salt;

    public PsswdSalt() {
    }

    public PsswdSalt(String id, int userId, String salt) {
        this.id = id;
        this.userId = userId;
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsswdSalt psswdSalt1 = (PsswdSalt) o;
        return userId == psswdSalt1.userId && Objects.equals(id, psswdSalt1.id) && Objects.equals(salt, psswdSalt1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, salt);
    }

    @Override
    public String toString() {
        return "Salt{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", salt='" + salt + '\'' +
                '}';
    }
}
