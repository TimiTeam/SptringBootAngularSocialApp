package com.gmail.timatiblackstar.authentication_example.models;

import javax.persistence.*;

@Entity
@Table(name = "sex")
public class Sex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ESex name;

    public Sex() {
    }

    public Sex(Integer id, ESex name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ESex getName() {
        return name;
    }

    public void setName(ESex name) {
        this.name = name;
    }
}
