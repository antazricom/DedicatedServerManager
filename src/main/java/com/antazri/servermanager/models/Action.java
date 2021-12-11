package com.antazri.servermanager.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "action")
@NamedQueries({
        @NamedQuery(name = "Action.FindAll",
                query = "SELECT a FROM Action a"),
        @NamedQuery(name = "Action.FindByApplication",
                query = "SELECT a FROM Action a " +
                        "WHERE a.application.id = :id"),
        @NamedQuery(name = "Action.FindByType",
                query = "SELECT a FROM Action a " +
                        "WHERE a.type = :type"),
        @NamedQuery(name = "Action.findByCreatedAt",
                query = "SELECT a FROM Action a " +
                        "WHERE a.createdAt BETWEEN :start AND :end"),
        @NamedQuery(name = "Action.findByApplicationAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual",
                query = "SELECT a FROM Action a " +
                        "WHERE a.application.id = :id " +
                        "AND (a.createdAt BETWEEN :start AND :end)")
})
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_id_seq")
    @SequenceGenerator(name = "action_id_seq", allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActionType type;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Application.class)
    private Application application;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Action() {
    }

    private Action(ActionType type, String description, Application application) {
        this.type = type;
        this.description = description;
        this.application = application;
    }

    public static Action create(ActionType type, String description, Application application) {
        return new Action(type, description, application);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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
        Action action = (Action) o;
        return id == action.id
                && type == action.type
                && Objects.equals(description, action.description)
                && Objects.equals(application, action.application)
                && Objects.equals(createdAt, action.createdAt)
                && Objects.equals(updatedAt, action.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, application, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", application=" + application +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
