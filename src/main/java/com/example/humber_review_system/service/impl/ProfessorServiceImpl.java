// src/main/java/com/example/university/service/impl/ProfessorServiceImpl.java
package com.example.humber_review_system.service.impl;

import com.example.humber_review_system.dtos.ProfessorDTO;
import com.example.humber_review_system.entity.Professor;
import com.example.humber_review_system.exceptions.ResourceNotFoundException;
import com.example.humber_review_system.repository.ProfessorRepository;
import com.example.humber_review_system.repository.ReviewRepository;
import com.example.humber_review_system.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "professor";
    private final ReviewRepository reviewRepository;

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        Professor professor = modelMapper.map(professorDTO, Professor.class);
        professorRepository.save(professor);
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#professorId")
    public ProfessorDTO getProfessorById(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor with id "+ professorId + " not found"));
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#professorId")
    public String deleteProfessorById(Long professorId) {
        if (!professorRepository.existsById(professorId)) {
            throw new ResourceNotFoundException("Professor with id "+ professorId + " not found");
        }
        professorRepository.deleteById(professorId);
        return "Professor with id " + professorId + " deleted successfully";
    }

    @Override
    public Double getAverageRatingForProfessor(Long professorId) {
        if (!professorRepository.existsById(professorId)) {
            throw new ResourceNotFoundException("Professor with id "+ professorId + " not found");
        }
        Double averageRating = reviewRepository.findAverageRatingByProfessorId(professorId);
        return averageRating == null ? 0.0 : averageRating;
    }

}