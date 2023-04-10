package com.imsjpa.IMSJPA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id; // The ID of the student
    @Column
    public String name; // The name of the student
    @Column
    public String email; // The email of the student
}
