package com.example.demo.jpa.eclass.controller;

import com.example.demo.jpa.eclass.entity.EClass;
import com.example.demo.jpa.eclass.service.EclassService;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/eclass")
@RequiredArgsConstructor
@Slf4j
public class EclassController {

    private final EclassService eclassService;
    private final UserService userService;

    //--------------------------------- E-Class 생성/조회/삭제 ---------------------------------------
    // Eclass 생성
    @PostMapping("/create")
    public ResponseEntity<String> createEclass(@RequestBody EClass eClass){
        log.info("body확인 : " + eClass.toString());
        eclassService.saveEclass(eClass);
        return ResponseEntity.ok("Creation Successed!");
    }
    //Eclass 전체 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<EClass>> getEclassList() {
        List<EClass> EclassList = eclassService.getEclass();
        return ResponseEntity.ok(EclassList);
    }

    // Eclass uuid로 조회
    @GetMapping("/get")
    public ResponseEntity<Optional<EClass>> getEclass(@RequestParam String eClassUuid) {
        Optional<EClass> Eclass = eclassService.getEclassOne(eClassUuid);
        return ResponseEntity.ok(Eclass);
    }
    
    // Eclass 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEclassList(@RequestParam String eClassUuid) {
        boolean deleted = eclassService.deleteEclassByUuid(eClassUuid);
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }

    // eclass 수업에서 학생 삭제
    @DeleteMapping("/student/delete")
    public ResponseEntity<String> deleteStuentEclass(@RequestParam String eClassUuid, @RequestParam String studentName ) {
log.info("dsdfs : " + eClassUuid + " " + studentName);
        Optional<User> student = userService.findByName(studentName);

        boolean deleted = eclassService.deleteEclassUuidTableByUuidAndStudentId(eClassUuid,student.get().getId());
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }

    //Eclass 시작 체크 메서드
    @GetMapping("/status-check")
    public ResponseEntity<Boolean> getEclassStatus(@RequestParam String uuid) {
        boolean EclassStartedCheck = eclassService.isEClassStarted(uuid);
        log.info("E-Class 시작 여부 :" + EclassStartedCheck);
        return ResponseEntity.ok(EclassStartedCheck);
    }

    // 수업 시작 메서드
    @PatchMapping("/eclass-start")
    public ResponseEntity<Boolean> setEclassStatusTrue(@RequestParam String uuid) {
        log.info("시작 수업 uuid : " + uuid);
        boolean saveCheck = eclassService.startEClass(uuid);
        log.info("수업 시작 저장 확인 : " + saveCheck);

        return ResponseEntity.ok(saveCheck);

    }

    // 수업 종료 메서드
    @PatchMapping("/eclass-close")
    public ResponseEntity<Boolean> setEclassStatusClose(@RequestParam String uuid) {
        log.info("종료 수업 uuid : " + uuid);
        boolean saveCheck = eclassService.closeEClass(uuid);
        log.info("수업 종료 저장 확인 : " + saveCheck);

        return ResponseEntity.ok(saveCheck);

    }

    // lecture 데이터가 하나라도 eclass에 쓰이고 있으면 true 반환해 주는 메서드
    @GetMapping("/eclass-check")
    public ResponseEntity<Boolean> getEclassCheck(@RequestParam String lectureDataUuid) {
        boolean EclassStartedCheck = eclassService.isEClassExsist(lectureDataUuid);
        log.info("E-Class 존재 여부 :" + EclassStartedCheck);
        return ResponseEntity.ok(EclassStartedCheck);
    }




}
