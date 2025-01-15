package com.example.humber_review_system.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProfessorDTO implements Serializable {
    private Long id;
    private String name;
}