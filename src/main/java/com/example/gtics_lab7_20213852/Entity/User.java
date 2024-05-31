package com.example.gtics_lab7_20213852.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "authorizedResource")
    private Resource authorizedResource;

}