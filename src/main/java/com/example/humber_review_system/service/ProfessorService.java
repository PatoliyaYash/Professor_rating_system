package com.example.humber_review_system.service;

import com.example.humber_review_system.dtos.ProfessorDTO;

import java.util.List;



public interface ProfessorService {
    List<ProfessorDTO> getAllProfessors();
    ProfessorDTO createProfessor(ProfessorDTO professorDTO);
    ProfessorDTO getProfessorById(Long professorId);
    String deleteProfessorById(Long professorId);
    Double getAverageRatingForProfessor(Long professorId);
}
