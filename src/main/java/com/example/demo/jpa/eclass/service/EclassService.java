package com.example.demo.jpa.eclass.service;

import com.example.demo.jpa.eclass.entity.EClass;
import com.example.demo.jpa.eclass.repository.EclassRepository;
import com.example.demo.jpa.eclass.repository.EclassUuidTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EclassService {

    private final EclassRepository eclassRepository;
    private final EclassUuidTableRepository eclassUuidTableRepository;

    // ------------------------------------- E-Class 생성/조회/삭제 서비스 ------------------------------
    @Transactional
    public void saveEclass(EClass eClass){
        eclassRepository.save(eClass);
    }

    @Transactional
    public List<EClass> getEclass(){
        return eclassRepository.findAll();
    }

    @Transactional
    public boolean isEClassStarted(String eClassUuid) {
        return eclassRepository.findStartedEClassByUuid(eClassUuid).isPresent();
    }

    @Transactional
    public boolean isEClassExsist(String lectureDataUuid) {
        return eclassRepository.findEClassByLectureUuid(lectureDataUuid).isPresent();
    }

    @Transactional
    public boolean startEClass(String eClassUuid) {
        EClass eClass = eclassRepository.findById(eClassUuid)
                .orElseThrow(() -> new RuntimeException("EClass not found with UUID: " + eClassUuid));

        eClass.setEclassStart(true);

        // 저장된 엔티티를 반환하여 null이 아닌지 확인
        EClass savedEClass = eclassRepository.save(eClass);

        return savedEClass != null;
    }

    @Transactional
    public boolean closeEClass(String eClassUuid) {
        EClass eClass = eclassRepository.findById(eClassUuid)
                .orElseThrow(() -> new RuntimeException("EClass not found with UUID: " + eClassUuid));

        eClass.setEclassStart(false);

        // 저장된 엔티티를 반환하여 null이 아닌지 확인
        EClass savedEClass = eclassRepository.save(eClass);

        return savedEClass != null;
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

    @Transactional
    public boolean deleteEclassUuidTableByUuidAndStudentId(String eClassUuid, Long studentId) {
        if (eclassUuidTableRepository.existsByEclassUuidAndStudentId(eClassUuid, studentId)) {
            eclassUuidTableRepository.deleteByEclassUuidAndStudentId(eClassUuid, studentId);
            return true;
        } else {
            return false;
        }
    }



}
