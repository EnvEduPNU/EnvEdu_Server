//package com.example.demo.jpa.datacontrol.dataclassroom.controller;
//
//import com.example.demo.jpa.datacontrol.dataclassroom.domain.dto.ClassroomSequenceRequestDto;
//import com.example.demo.jpa.datacontrol.dataclassroom.service.ClassroomService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class ClassroomController {
//
//    private final ClassroomService classroomService;
//
//    @PostMapping("/dataLiteracy/classroom/e-class/new")
//    public ResponseEntity<?> generateClassroom(@RequestBody ClassroomSequenceRequestDto item, HttpServletRequest request){
//        String userName = (String) request.getAttribute("userName");
//        log.info("Username : " + userName);
//
//        classroomService.generateClassroom(userName, item);
//        //classroomService.generateClassroom("Educator1", item);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/dataLiteracy/classroom/mine")
//    public ResponseEntity<?> getClassroomByMine(HttpServletRequest request){
//        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
//        return new ResponseEntity<>(classroomService.getMyRelatedClassroom(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
//        //return new ResponseEntity<>(classroomService.getMyRelatedClassroom("Student1"), HttpStatus.OK);
//    }
//
//    @GetMapping("/dataLiteracy/classroom")
//    public ResponseEntity<?> getClassroomById(@RequestParam(required = false) Long id){
//        return new ResponseEntity<>(classroomService.getClassroomById(id), HttpStatus.OK);
//    }
//
//    @GetMapping("/dataLiteracy/classroom/list")
//    public ResponseEntity<?> getAllClassroomTest(@RequestParam(required = false) String grade, @RequestParam(required = false) String subject,
//                                                 @RequestParam(required = false) String dataType){
//        return new ResponseEntity<>(classroomService.findAllClassroomByGradeSubjectDataType(grade, subject, dataType), HttpStatus.OK);
//        //return new ResponseEntity<>(classroomService.findAllClassroomByGradeSubjectDataType(grade, subject, dataType), HttpStatus.OK);
//    }
//
//    @GetMapping("/dataLiteracy/classroom/searchTypes")
//    public ResponseEntity<?> getSearchTypes(){
//        return new ResponseEntity<>(classroomService.getSearchTypes(), HttpStatus.OK);
//    }
//}
