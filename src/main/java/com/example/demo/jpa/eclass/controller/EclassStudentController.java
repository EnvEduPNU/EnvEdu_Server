package com.example.demo.jpa.eclass.controller;

import com.example.demo.jpa.eclass.entity.EClassStudent;
import com.example.demo.jpa.eclass.entity.EClassUuid;
import com.example.demo.jpa.eclass.service.EclassStudentService;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/eclass/student")
@Slf4j
public class EclassStudentController {

    private final EclassStudentService eclassStudentService;
    private final UserService userService;


    // --------------------------------- E-Class Student 조회/삽입/삭제 ---------------------------------
    @PostMapping("/enroll")
    public ResponseEntity<String> joinStudent(@RequestBody Map<String, Object> enrollStudent) {
        try {
            // ---------------------- EClassStudent 저장 ------------------------------
            Long studentId = Long.parseLong(enrollStudent.get("studentId").toString());
            String studentName = (String) enrollStudent.get("studentName");
            String studentGroup = (String) enrollStudent.get("studentGroup");
            String joinDate = (String) enrollStudent.get("joinDate");
            // 학생 정보 객체 생성
            EClassStudent student = new EClassStudent();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentGroup(studentGroup);
            student.setJoinDate(joinDate);
            // 서비스 호출하여 저장
            eclassStudentService.saveStudent(student);

            // ---------------------- EClassUuid 저장 -------------------------------
            List<String> eclassUuid = (List<String>) enrollStudent.get("eclassUuid");

            log.info("eclassUuid 확인 : " + eclassUuid.get(0));
            log.info("studentId 확인 : " + studentId);

            EClassUuid uuid = new EClassUuid();
            uuid.setEclassUuid(eclassUuid.get(0));
            uuid.setStudentId(studentId);

            eclassStudentService.saveUuid(uuid);



            return ResponseEntity.ok("Student " + studentId + " has successfully joined.");
        } catch (NullPointerException | ClassCastException e) {
            // 입력 데이터 오류 처리
            return ResponseEntity.badRequest().body("Error: Invalid input data.");
        }
    }



    // 해당 E-Class에 참여한 모든 학생들의 리스트를 가져오는 메서드 (E-Class Controller로 옮김 예정)
    @GetMapping("/joinList")
    public ResponseEntity<List<Optional<EClassStudent>>> getJoinedStudentList(@RequestParam String eclassUuid) {
        log.info("eclassUuid 어떻게 되는데 : " + eclassUuid);

        List<EClassUuid> EclassList = eclassStudentService.getEclassUuidByUuid(eclassUuid);

        log.info("리스트가 어떻게 되는데 : " + EclassList);

        // EclassList의 각 항목에 대해 studentId를 사용하여 EClassStudent 정보를 조회하고 리스트에 추가
        List<Optional<EClassStudent>> EclassStudents = EclassList.stream()
                .map(eclassUuidItem -> eclassStudentService.findByStudentId(eclassUuidItem.getStudentId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(EclassStudents);
    }

    @GetMapping("/allList")
    public ResponseEntity<List<User>> getAllStudentList() {
        List<User> EclassList = userService.getAllStudents();
        return ResponseEntity.ok(EclassList);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam long studentId) {
        boolean deleted = eclassStudentService.deleteStudentByUuid(studentId);
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }

    @GetMapping("/addEclassUuid")
    public ResponseEntity<String> addEclassUuid(@RequestParam String studentName, @RequestParam String eclassUuid) {


        log.info("uuid 넘어오긴 함 : " + eclassUuid);

        try {
            eclassStudentService.addEclassUuid(studentName, eclassUuid);
            return ResponseEntity.ok("EclassUuid가 성공적으로 추가되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/eclassUuids")
    public ResponseEntity<List<String>> getEclassUuidsByStudentName(@RequestParam String studentName) {


        try {
            List<String> eclassUuids = eclassStudentService.getEclassUuidsByStudentName(studentName);
            return ResponseEntity.ok(eclassUuids);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
