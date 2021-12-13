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
        @NamedQuery(name = "Application.FindByStatus",
                query = "SELECT a FROM Application a " +
                        "WHERE a.status = :status"),
        @NamedQuery(name = "Application.updateStatus",
                query = "UPDATE Application a " +
                        "SET a.status = :status " +
                        "WHERE a.id = :id"),
        @NamedQuery(name = "Application.FindAll",
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppStatus status;

    @OneToMany(mappedBy = "application", targetEntity = Action.class, cascade = CascadeType.ALL)
    private List<Action> actions;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Application() {
    }

    private Application(String name, AppStatus status) {
        this.name = name;
        this.status = status;
    }

    private Application(String name, AppStatus status, List<Action> actions) {
        this(name, status);
        this.actions = actions;
    }

    private Application(int id, String name, AppStatus status, List<Action> actions) {
        this(name, status, actions);
        this.id = id;
    }

    public static Application create(String name, AppStatus status) {
        return new Application(name, status);
    }

    public static Application from(int id, String name, AppStatus status, List<Action> actions) {
        return new Application(id, name, status, actions);
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

    public AppStatus getStatus() {
        return status;
    }

    public void setStatus(AppStatus status) {
        this.status = status;
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
                && status == that.status
                && Objects.equals(actions, that.actions)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, actions, createdAt, updatedAt);
    }
}
