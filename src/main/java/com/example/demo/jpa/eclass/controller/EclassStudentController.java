package com.example.demo.jpa.eclass.controller;

import com.example.demo.jpa.eclass.dto.AssginmentStepListDTO;
import com.example.demo.jpa.eclass.dto.AssignmentStepCheckDTO;
import com.example.demo.jpa.eclass.dto.AssignmentUuidDTO;
import com.example.demo.jpa.eclass.dto.ReportDTO;
import com.example.demo.jpa.eclass.entity.EClassStudent;
import com.example.demo.jpa.eclass.entity.EClassUuid;
import com.example.demo.jpa.eclass.service.EClassUuidService;
import com.example.demo.jpa.eclass.service.EclassStudentService;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/eclass/student")
@Slf4j
public class EclassStudentController {

    private final EclassStudentService eclassStudentService;
    private final UserService userService;
    private final EClassUuidService eClassUuidService;


    // --------------------------------- E-Class Student 조회/삽입/삭제 ---------------------------------
    @PostMapping("/enroll")
    public ResponseEntity<String> joinStudent(@RequestBody Map<String, Object> enrollStudent) {
        try {
            // ---------------------- EClassStudent 저장 ------------------------------
            Long studentId = Long.parseLong(enrollStudent.get("studentId").toString());
            String studentName = (String) enrollStudent.get("studentName");
            String studentGroup = (String) enrollStudent.get("studentGroup");
            String joinDate = (String) enrollStudent.get("joinDate");
            List<String> eclassUuidList = (List<String>) enrollStudent.get("eclassUuid");
            String eclassUuid = eclassUuidList.get(0); // 첫 번째 UUID 사용

            // 이미 등록된 학생인지 확인
            if (eclassStudentService.isStudentAlreadyEnrolled(studentId, eclassUuid)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: The student is already enrolled in this class.");
            }

            // 학생 정보 객체 생성
            EClassStudent student = new EClassStudent();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentGroup(studentGroup);
            student.setJoinDate(joinDate);
            eclassStudentService.saveStudent(student);

            // ---------------------- EClassUuid 저장 -------------------------------
            EClassUuid uuid = new EClassUuid();
            uuid.setEclassUuid(eclassUuid);
            uuid.setStudentId(studentId);
            eclassStudentService.saveUuid(uuid);

            return ResponseEntity.ok("Student " + studentId + " has successfully joined.");
        } catch (NullPointerException | ClassCastException e) {
            // 입력 데이터 오류 처리
            return ResponseEntity.badRequest().body("Error: Invalid input data.");
        }
    }

    @DeleteMapping("/joined/delete")
    public ResponseEntity<String> deleteStudentInEclass(@RequestParam String eClassUuid) {
        boolean deleted =   eclassStudentService.deleteAllByEclassUuid(eClassUuid);
        if (deleted) {
            return ResponseEntity.ok("Deleted Completed!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EClass not found");
        }
    }


    // 해당 E-Class에 참여한 모든 학생들의 리스트를 가져오는 메서드 (E-Class Controller로 옮김 예정)
    @GetMapping("/joinList")
    public ResponseEntity<List<EClassStudent>> getJoinedStudentList(@RequestParam String eclassUuid) {
        log.info("eclassUuid 어떻게 되는데 : " + eclassUuid);

        List<EClassUuid> eclassList = eclassStudentService.getEclassUuidByUuid(eclassUuid);

        log.info("리스트가 어떻게 되는데 : " + eclassList);

        // EclassList의 각 항목에 대해 studentId를 사용하여 EClassStudent 정보를 조회하고 리스트에 추가
        List<EClassStudent> eclassStudents = eclassList.stream()
                .map(eclassUuidItem -> eclassStudentService.findByStudentId(eclassUuidItem.getStudentId()))
                .filter(Optional::isPresent) // Optional이 존재하는 경우만 필터링
                .map(Optional::get) // Optional에서 실제 값을 가져옴
                .collect(Collectors.toList());

        return ResponseEntity.ok(eclassStudents);
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

    @PostMapping("/assginmentUuid/update")
    public ResponseEntity<String> setAssignmentUuidByUsernameAndEclassUuid(@RequestBody AssignmentUuidDTO assignmentUuidDTO) {
        try {
            Optional<User> studentData = userService.findByName(assignmentUuidDTO.getUsername());

            long studentId = studentData.get().getId();

            log.info("[uuid 업데이트] uuid : " + assignmentUuidDTO.getEclassUuid());
            log.info("[uuid 업데이트] id : " + studentId);


            Optional<EClassUuid> assignData = eClassUuidService.findByEclassUuidAndStudentId(assignmentUuidDTO.getEclassUuid(), studentId);

            assignData.ifPresent(eClassUuid -> eClassUuidService.updateAssignmentUuid(eClassUuid.getId(), assignmentUuidDTO.getAssginmentUuid()));

            return ResponseEntity.ok("update successed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/assginmentUuid/get")
    public ResponseEntity<String> getAssignmentUuidByUsernameAndEclassUuid(@RequestBody AssignmentUuidDTO assignmentUuidDTO) {

        try {
            Optional<User> studentData = userService.findByName(assignmentUuidDTO.getUsername());

            if (studentData.isPresent()) {
                long studentId = studentData.get().getId();

                log.info("스튜던트 아이디 : " + studentId);
                log.info("getEclassUuid 아이디 : " + assignmentUuidDTO.getEclassUuid());


                Optional<EClassUuid> assignData = eClassUuidService.findByEclassUuidAndStudentId(assignmentUuidDTO.getEclassUuid(), studentId);

                log.info("assignmentUuid 아이디 : " + assignData.get().getAssignmentUuid());

                String assignmentUuid = assignData.get().getAssignmentUuid();
                return ResponseEntity.ok(assignmentUuid != null ? assignmentUuid : "noData");
            }

            return ResponseEntity.ok("noData");  // 조건을 만족하지 못하면 null 반환
        } catch (RuntimeException e) {
            // 예외가 발생해도 null 반환
            return ResponseEntity.ok("noData");
        }
    }



    @PostMapping("/assignment/stepCheck")
    public ResponseEntity<String> setAssignmentStepCheck(@RequestBody AssignmentStepCheckDTO assignmentStepCheckDTO) {

        boolean[] stepCheckArray = assignmentStepCheckDTO.getStepCheck();
        Long studentId = assignmentStepCheckDTO.getStudentId();

        log.info("studentId ? : " + studentId);

        // boolean[]을 List<Boolean>으로 변환
        List<Boolean> stepCheckList = IntStream.range(0, stepCheckArray.length)
                .mapToObj(i -> stepCheckArray[i])
                .collect(Collectors.toList());

        // studentId에 맞는 assignmentData 업데이트
        eClassUuidService.updateAssignmentData(studentId, stepCheckList);

        return ResponseEntity.ok("success!");
    }

    @GetMapping("/assignment/stepCheck/{id}")
    public ResponseEntity<boolean[]> getAssignmentStepCheck(@PathVariable Long id) {
        boolean[] stepCheckArray = eClassUuidService.getAssignmentData(id);

        return ResponseEntity.ok(stepCheckArray);
    }


    @PostMapping("/assignment/getCheckList")
    public ResponseEntity<Map<String, boolean[]>> handleStudentAssignmentCheck(
            @RequestBody AssginmentStepListDTO assginmentStepListDTO) {

        String eclassUuid = assginmentStepListDTO.getEclassUuid();
        List<String> studentData = assginmentStepListDTO.getStudentData();

        // 학생의 username을 키로, assignmentData를 값으로 하는 맵을 생성
        Map<String, boolean[]> assignmentDataMap = new HashMap<>();

        // studentData 리스트의 각 username에 대해 studentId를 조회하고 assignmentData를 가져옴
        for (String username : studentData) {
            Long studentId = eclassStudentService.getEclassStudentId(username, eclassUuid);
            log.info("학생 아이디 : " + studentId);

            boolean[] assignmentData = eClassUuidService.getAssignmentData(studentId);
            log.info("학생 스텝체크 : " + Arrays.toString(assignmentData));

            assignmentDataMap.put(username, assignmentData);
        }

        log.info("제대로 나오는지 크기로 확인 : " + assignmentDataMap.size());

        // 결과 반환
        return ResponseEntity.ok(assignmentDataMap);
    }

    // Report Uuid 가져 오는 메서드
    @PostMapping("/assignment/reportUuid/get")
    public ResponseEntity<String> getReportData(
            @RequestBody AssginmentStepListDTO assginmentStepListDTO) {

        String eclassUuid = assginmentStepListDTO.getEclassUuid();
        List<String> studentData = assginmentStepListDTO.getStudentData();

        String reportUuid = null;
        for (String username : studentData) {
            Long studentId = eclassStudentService.getEclassStudentId(username, eclassUuid);
            log.info("학생 아이디 : " + studentId);

            reportUuid = eClassUuidService.getReportUuid(studentId);
        }
        return ResponseEntity.ok(reportUuid);
    }

    /**
     * 보고서 제출 및 저장하는 메서드
     * @param reportDTO
     * @Author 김선규
     * @return 저장 결과
     */
    @PostMapping("/assignment/report/save")
    public ResponseEntity<String> saveReportUuid(@RequestBody ReportDTO reportDTO) {
        String studentId = reportDTO.getStudentId();
        String reportUuid = reportDTO.getReportUuid();

        try {
            boolean isUpdated = eClassUuidService.updateReportData(Long.valueOf(studentId), reportUuid);

            if (isUpdated) {
                return ResponseEntity.ok("Report updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update report");
            }
        } catch (RuntimeException e) {
            // EClassUuid를 찾지 못했을 때의 예외 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // 보고서 테이블에 누가 보고서를 썼는지 학생 이름과 보고서를 반환해주는 메서드
    @GetMapping("/assignment/report/get/{eclassUuid}")
    public ResponseEntity<Map<String, String>> getReportAndStudent(@PathVariable String eclassUuid) {
        List<EClassUuid> eclassUuidData = eClassUuidService.getReportByEclassUuid(eclassUuid);

        Map<String, String> reportInfoMap = new HashMap<>();

        for (EClassUuid eclassUuidObj : eclassUuidData) {
            Long studentId = eclassUuidObj.getStudentId();
            Optional<User> studentData = userService.findById(studentId); // studentId로 User 객체 찾기
            String username = null;
            if(studentData.isPresent()){
                username = studentData.get().getUsername();
            }
            String reportData = eclassUuidObj.getReportData();

            reportInfoMap.put(reportData, username);
        }

        return ResponseEntity.ok(reportInfoMap);
    }

}
