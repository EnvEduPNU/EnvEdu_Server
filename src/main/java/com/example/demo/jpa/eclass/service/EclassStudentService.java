package com.example.demo.jpa.eclass.service;

import com.example.demo.jpa.eclass.entity.EClassStudent;
import com.example.demo.jpa.eclass.entity.EClassUuid;
import com.example.demo.jpa.eclass.repository.EClassUuidRepository;
import com.example.demo.jpa.eclass.repository.EclassStudentRepository;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EclassStudentService {

    private final EclassStudentRepository eclassStudentRepository;
    private final UserService userService;
    private final EClassUuidRepository eClassUuidRepository;
    private final UserRepository userRepository;

    // -------------------------------------- E-Class 학생 참여/조회/삭제 -------------------------------
    @Transactional
    public void saveStudent(EClassStudent eClassStudent){
        eclassStudentRepository.save(eClassStudent);
    }

    @Transactional
    public void saveUuid(EClassUuid eClassUuid){
        eClassUuidRepository.save(eClassUuid);
    }

    @Transactional
    public boolean deleteStudentByUuid(long studentId) {
        // studentId가 존재하는지 확인
        if (eclassStudentRepository.existsByStudentId(studentId)) {
            // studentId를 기준으로 삭제
            eclassStudentRepository.deleteByStudentId(studentId);
            return true;
        } else {
            log.info("학생이 존재하지 않습니다.");
            return false;
        }
    }
    @Transactional
    public boolean deleteAllByEclassUuid(String eclassUuid) {

        if (eClassUuidRepository.existsByEclassUuid(eclassUuid)) {
            // studentId를 기준으로 삭제
            eClassUuidRepository.deleteByEclassUuid(eclassUuid);
            return true;
        } else {
            log.info("학생이 존재하지 않습니다.");
            return false;
        }

    }

    public boolean isStudentAlreadyEnrolled(Long studentId, String eclassUuid) {
        return eClassUuidRepository.existsByStudentIdAndEclassUuid(studentId, eclassUuid);
    }


    public Optional<EClassStudent> findByStudentId(Long studentId) {
        return Optional.ofNullable(eclassStudentRepository.findByStudentId(studentId));
    }

    @Transactional
    public EClassStudent addEclassUuid(String studentName, String eclassUuid) {
        Optional<EClassStudent> studentOptional = eclassStudentRepository.findByStudentName(studentName);

        EClassStudent student;
        if (studentOptional.isPresent()) {
            student = studentOptional.get();
        } else {
            // UserService를 사용해 해당 유저 정보 가져오기
            User user = userService.findByName(studentName)
                    .orElseThrow(() -> new RuntimeException("해당 이름의 유저를 찾을 수 없습니다."));

            // EClassStudent 객체 생성 및 속성 설정
            student = new EClassStudent();
            student.setStudentName(user.getUsername());
            student.setStudentId(user.getId());  // User ID를 EClassStudent의 studentId로 설정
            student.setStudentGroup(user.getStudentGroup());  // 필요시 유저 그룹 설정
            student.setJoinDate(LocalDate.now().toString()); // 현재 날짜를 joinDate로 설정

            // 학생을 처음 등록
            student = eclassStudentRepository.save(student);
        }

        // 새로운 EClassUuid 엔티티 생성 및 설정
        EClassUuid eclassUuidEntity = new EClassUuid();
        eclassUuidEntity.setEclassUuid(eclassUuid);
        eclassUuidEntity.setStudentId(student.getStudentId());



        // 변경된 정보 저장
        return eclassStudentRepository.save(student);
    }


    @Transactional
    public List<String> getEclassUuidsByStudentName(String studentName) {
        Optional<EClassStudent> studentOptional = eclassStudentRepository.findByStudentName(studentName);

        if (studentOptional.isPresent()) {
            EClassStudent student = studentOptional.get();

            Long studentId = student.getStudentId();

            List<EClassUuid> eClassUuidList = eClassUuidRepository.findByStudentId(studentId);

            return eClassUuidList.stream()
                    .map(EClassUuid::getEclassUuid)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("해당 이름의 학생을 찾을 수 없습니다.");
        }
    }

    public List<EClassUuid> getEclassUuidByUuid(String eclassUuid) {
        return eClassUuidRepository.findByEclassUuid(eclassUuid);
    }

    public long getEclassStudentId(String username, String uuid) {
        // username을 통해 studentId 조회

        Optional<EClassStudent> studentOptional = eclassStudentRepository.findByStudentName(username);

        long studentId = studentOptional.get().getStudentId();

        Optional<Long> studentIdOpt = Optional.of(studentId);

        if (studentIdOpt.isEmpty()) {
            log.warn("학생을 찾을 수 없습니다: {}", username);
            throw new RuntimeException("학생을 찾을 수 없습니다: " + username);
        }

        log.info("학생 ID 확인: {}", studentId);
        log.info("uuid 확인: {}", uuid);

        // uuid와 studentId를 통해 EClassUuid 조회
        Optional<EClassUuid> eclassStudentOpt = eClassUuidRepository.findIdByEclassUuidAndStudentId(uuid, studentId);

        // Optional에서 EClassUuid의 ID 추출
        EClassUuid eclassStudent = eclassStudentOpt
                .orElseThrow(() -> new RuntimeException("EClassUuid not found for uuid: " + uuid + " and studentId: " + studentId));

        long eclassStudentId = eclassStudent.getId();
        log.info("EclassStudentId 확인: {}", eclassStudentId);

        return eclassStudentId;
    }



}
