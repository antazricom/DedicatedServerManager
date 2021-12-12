package com.antazri.servermanager.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "application")
@NamedQueries({
        @NamedQuery(name = "Application.Exists",
                query = "SELECT a FROM Application a WHERE a.id = :id")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Application.FindByName",
                query = "SELECT a FROM Application a " +
                        "WHERE a.name ilike '%'+ :name +'%' " +
                        "ORDER BY a.name"),
        @NamedNativeQuery(name = "Application.FindAll",
                query = "SELECT a FROM Application a " +
                        "ORDER BY a.name")
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_id_seq")
    @SequenceGenerator(name = "application_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "application", targetEntity = Action.class, cascade = CascadeType.ALL)
    private List<Action> actions;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Application() {
    }

    private Application(String name) {
        this.name = name;
    }

    private Application(int id, String name, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.actions = actions;
    }

    public static Application create(String name) {
        return new Application(name);
    }

    public static Application from(int id, String name, List<Action> actions) {
        return new Application(id, name, actions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id
                && Objects.equals(name, that.name)
                && Objects.equals(actions, that.actions)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, actions, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actions=" + actions +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
