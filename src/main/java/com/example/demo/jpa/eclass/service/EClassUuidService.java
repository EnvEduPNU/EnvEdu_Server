package com.example.demo.jpa.eclass.service;

import com.example.demo.jpa.eclass.entity.EClassUuid;
import com.example.demo.jpa.eclass.repository.EClassUuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EClassUuidService {

    private final EClassUuidRepository eClassUuidRepository;

    // id에 맞는 assignmentData를 업데이트하는 메서드
    public void updateAssignmentData(Long id, List<Boolean> assignmentData) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(id);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();
            eClassUuid.setAssignmentData(assignmentData); // assignmentData 업데이트
            eClassUuidRepository.save(eClassUuid); // 변경사항 저장
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + id);
        }
    }

    public void updateAssignmentUuid(Long id, String assignmentUuid) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(id);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();
            eClassUuid.setAssignmentUuid(assignmentUuid); // assignmentData 업데이트
            eClassUuidRepository.save(eClassUuid); // 변경사항 저장
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + id);
        }
    }

    public String getAssignmentUuid(Long id) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(id);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();
            return eClassUuid.getAssignmentUuid();
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + id);
        }
    }

    // id에 맞는 reportData를 업데이트 하는 메서드
    public boolean updateReportData(Long studentId, String reportUuid) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(studentId);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();
            eClassUuid.setReportData(reportUuid); // reportUuid 업데이트
            eClassUuidRepository.save(eClassUuid); // 변경사항 저장
            return true; // 업데이트 성공
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + studentId);
        }
    }


    public boolean[] getAssignmentData(Long id) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(id);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();
            List<Boolean> assignmentData = eClassUuid.getAssignmentData();

            // List<Boolean>을 boolean[]으로 변환
            boolean[] stepCheckArray = new boolean[assignmentData.size()];
            for (int i = 0; i < assignmentData.size(); i++) {
                stepCheckArray[i] = assignmentData.get(i);
            }
            return stepCheckArray;
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + id);
        }
    }

    public String getReportUuid(Long id) {
        Optional<EClassUuid> optionalEClassUuid = eClassUuidRepository.findById(id);

        if (optionalEClassUuid.isPresent()) {
            EClassUuid eClassUuid = optionalEClassUuid.get();

            return eClassUuid.getReportData();
        } else {
            throw new RuntimeException("EClassUuid not found for id: " + id);
        }
    }

    public Optional<EClassUuid> findByEclassUuidAndStudentId(String eclassUuid, Long studentId) {
        return eClassUuidRepository.findIdByEclassUuidAndStudentId(eclassUuid, studentId);
    }

    public boolean[] getAssignmentDataForStudent(Long studentId, String eclassUuid) {
        Optional<EClassUuid> optionalEClassUuid = findByEclassUuidAndStudentId(eclassUuid, studentId);
        if (optionalEClassUuid.isPresent()) {
            List<Boolean> assignmentDataList = optionalEClassUuid.get().getAssignmentData();
            boolean[] assignmentDataArray = new boolean[assignmentDataList.size()];
            for (int i = 0; i < assignmentDataList.size(); i++) {
                assignmentDataArray[i] = assignmentDataList.get(i);
            }
            return assignmentDataArray;
        }
        throw new RuntimeException("EClassUuid not found for studentId: " + studentId + " and eclassUuid: " + eclassUuid);
    }

    public List<EClassUuid> getReportByEclassUuid(String eclassUuid) {
        return eClassUuidRepository.findByEclassUuid(eclassUuid);
    }

}
