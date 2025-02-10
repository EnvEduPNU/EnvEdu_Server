package com.example.demo.jpa.user.controller;

import com.example.demo.jpa.exceptions.CustomMailException;
import com.example.demo.jpa.exceptions.DuplicateAttributeException;
import com.example.demo.jpa.user.service.UserService;
//import com.example.demo.jpa.jwt.util.JwtUtil;
//import com.example.demo.jpa.user.dto.request.EmailDTO;
import com.example.demo.jpa.user.dto.request.RegisterDTO;
//import com.example.demo.jpa.user.dto.request.StudentAddDTO;
//import com.example.demo.jpa.user.dto.response.Student_EducatorDTO;
import com.example.demo.jpa.user.util.PasswordUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
//import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRegisterController {
    private final UserService userService;
//    @RequestMapping(value = "/student/join/{inviteCode}", method = {RequestMethod.GET, RequestMethod.POST})
//    private ResponseEntity<?> postJoinFromInviteCode(@PathVariable String inviteCode, HttpServletRequest request){
//        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
//        //InviteCode inviteCode = userService.generateInviteCode(userInfo.get(JwtUtil.claimUsername).toString());
//
//        return new ResponseEntity<>(userService.joinInviteCode(userInfo.get(JwtUtil.claimUsername).toString(), inviteCode), HttpStatus.OK);
//    }
//
//    @GetMapping("/educator/inviteCode/generate")
//    private ResponseEntity<?> generateInviteCode(HttpServletRequest request){
//        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
//        //InviteCode inviteCode = userService.generateInviteCode(userInfo.get(JwtUtil.claimUsername).toString());
//
//        return new ResponseEntity<>(userService.generateInviteCode(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
//    }
//
//    /**
//     * 일반 user, student 관련 api
//    */
//    @PostMapping("/auth")
//    private ResponseEntity<?> sendAuthNum(@Valid @RequestBody EmailDTO emailDTO) {
//        userService.sendAuthNum(emailDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/auth")
//    private ResponseEntity<?> checkAuthNum(@RequestParam(value = "email") String email,
//                                           @RequestParam(value = "authNum") String authNum) {
//        userService.checkAuthNum(email, authNum);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    // 학생 등록 메서드
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String bodyContent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterDTO registerDTO = objectMapper.readValue(bodyContent, RegisterDTO.class);

            // salt 생성 및 비밀번호 해싱
//            byte[] salt = PasswordUtils.generateSalt();
//            String hashedPassword = PasswordUtils.hashPassword(registerDTO.getPassword(), salt);
//            registerDTO.setPassword(hashedPassword);

            log.info("DTO 체크 : " + registerDTO.getStudentGroup());

            userService.addUser(registerDTO);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (DuplicateAttributeException e) {
            // 중복 예외 처리
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(e.getMessage());

        } catch (JsonProcessingException e) {
            // JSON 처리 예외 처리
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                    .body("잘못된 요청 형식입니다.");

        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
                    .body("서버 오류가 발생했습니다.");
        }
    }


//    @PostMapping("/educator/student")
//    private ResponseEntity<?> registerStudent(HttpServletRequest request, @RequestBody StudentAddDTO studentAddDTO) {
//        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
//        userService.addStudent(userInfo, studentAddDTO);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/educator/student_educator")
//    private ResponseEntity<Student_EducatorDTO> getEducatingStudents(HttpServletRequest request) {
//        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
//        Student_EducatorDTO result = userService.getEducatingStudents(userInfo);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    private ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
//    {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e)
//    {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(CustomMailException.class)
//    private ResponseEntity<?> customMailExceptionHandler(CustomMailException e)
//    {
//       return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(DuplicateAttributeException.class)
//    private ResponseEntity<?> duplicateAttributeExceptionHandler(DuplicateAttributeException e) {
//        return new ResponseEntity<>("중복되는 " + e.getAttribute() + "(이)가 있습니다", HttpStatus.BAD_REQUEST);
//    }
}
