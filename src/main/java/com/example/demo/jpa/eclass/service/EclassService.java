package com.example.demo.jpa.eclass.service;

import com.example.demo.jpa.eclass.dto.EClassDTO;
import com.example.demo.jpa.eclass.entity.EClass;
import com.example.demo.jpa.eclass.repository.EclassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EclassService {

    private final EclassRepository eclassRepository;

    @Transactional
    public void saveEclass(EClass eClass){
        eclassRepository.save(eClass);
    }

    @Transactional
    public List<EClass> getEclass(){
        return eclassRepository.findAll();
    }

    @Transactional
    public boolean deleteEclassByUuid(String eClassUuid) {
        if (eclassRepository.existsById(eClassUuid)) {
            eclassRepository.deleteById(eClassUuid);
            return true;
        } else {
            return false;
        }
    }
}
