package ru.makarov.springripper.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "department_person", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Person> person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }
}
