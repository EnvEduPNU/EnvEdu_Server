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
    @PostMapping("/create")
    public ResponseEntity<String> createEclass(@RequestBody EClass eClass){
        log.info("body확인 : " + eClass.toString());
        eclassService.saveEclass(eClass);
        return ResponseEntity.ok("Creation Successed!");
    }
    @GetMapping("/list")
    public ResponseEntity<List<EClass>> getEclassList() {
        List<EClass> EclassList = eclassService.getEclass();
        return ResponseEntity.ok(EclassList);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEclassList(@RequestParam String eClassUuid) {
        boolean deleted = eclassService.deleteEclassByUuid(eClassUuid);
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }

    @DeleteMapping("/stuent/delete")
    public ResponseEntity<String> deleteStuentEclass(@RequestParam String eClassUuid, @RequestParam String studentName ) {

        Optional<User> student = userService.findByName(studentName);

        boolean deleted = eclassService.deleteEclassUuidTableByUuidAndStudentId(eClassUuid,student.get().getId());
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }

    @GetMapping("/status-check")
    public ResponseEntity<Boolean> getEclassStatus(@RequestParam String uuid) {
        boolean EclassStartedCheck = eclassService.isEClassStarted(uuid);
        log.info("E-Class 시작 여부 :" + EclassStartedCheck);
        return ResponseEntity.ok(EclassStartedCheck);
    }

    @PatchMapping("/eclass-start")
    public ResponseEntity<Boolean> setEclassStatusTrue(@RequestParam String uuid) {
        log.info("시작 수업 uuid : " + uuid);
        boolean saveCheck = eclassService.startEClass(uuid);
        log.info("수업 시작 저장 확인 : " + saveCheck);

        return ResponseEntity.ok(saveCheck);

    }

    @PatchMapping("/eclass-close")
    public ResponseEntity<Boolean> setEclassStatusClose(@RequestParam String uuid) {
        log.info("종료 수업 uuid : " + uuid);
        boolean saveCheck = eclassService.closeEClass(uuid);
        log.info("수업 종료 저장 확인 : " + saveCheck);

        return ResponseEntity.ok(saveCheck);

    }




}
