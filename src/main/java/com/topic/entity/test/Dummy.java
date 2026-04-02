package com.topic.entity.test;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dummies")
@Data
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
