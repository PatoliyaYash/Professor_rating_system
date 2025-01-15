package com.example.humber_review_system.controller;

import com.example.humber_review_system.dtos.ProfessorDTO;
import com.example.humber_review_system.service.impl.ProfessorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class        ProfessorController {

    private final ProfessorServiceImpl professorService;

    @GetMapping
    public List<ProfessorDTO> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @PostMapping("/create")
    @Secured({"ROLE_ADMIN"})
    public ProfessorDTO createProfessor(@RequestBody ProfessorDTO professorDTO) {
        return professorService.createProfessor(professorDTO);
    }

    @GetMapping("/{professorId}")
    public ProfessorDTO getProfessorById(@PathVariable Long professorId) {
        return professorService.getProfessorById(professorId);
    }

    @DeleteMapping("delete/{professorId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> deleteProfessorById(@PathVariable Long professorId) {
        professorService.deleteProfessorById(professorId);
        return new ResponseEntity<>("Professor with id " + professorId + " deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/average-rating/{professorId}")
    public Double getAverageRatingForProfessor(@PathVariable Long professorId) {
        return professorService.getAverageRatingForProfessor(professorId);
    }
}