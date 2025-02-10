package com.example.demo.jpa.user.service;


import com.example.demo.jpa.exceptions.DuplicateAttributeException;
import com.example.demo.jpa.user.dto.request.RegisterDTO;
import com.example.demo.jpa.user.model.entity.*;
import com.example.demo.jpa.user.model.enumerate.Role;
import com.example.demo.jpa.user.repository.*;

import com.example.demo.jpa.user.util.UserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Student_EducatorRepository student_educatorRepository;


    /**
     register service
     */
    @Transactional
    public void addUser(RegisterDTO registerDTO) {

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
//        user.setEmail(registerDTO.getEmail());
//        user.setGender(registerDTO.getGender());
        user.setRole(registerDTO.getRole());
//        user.setStudentGroup(registerDTO.getStudentGroup());
//        user.setBirthday(registerDTO.getBirthday());
//        user.setNickname(registerDTO.getNickname());

        // 중복된 아이디 체크
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new DuplicateAttributeException("아이디");
//        }

        // 중복된 이메일 체크
//        if (userRepository.existsByEmail(registerDTO.getEmail())) {
//            throw new DuplicateAttributeException("이메일");
//        }


        userRepository.save(user);
    }


    public List<User> getAllStudents() {
        return userRepository.findByRole("ROLE_STUDENT");
    }

    public List<Student_Educator> findStudentsByStudentOrEducator(String username){
        User user = userRepository.findByUsername(username).get();

        if (user instanceof Student){
            Student_Educator educatorByStudent = findEducatorByStudent((Student) user);
            user = educatorByStudent.getEducator();
        }
        return findAllByEducator((Educator) user);
    }

    @Transactional
    public Student_Educator findEducatorByStudent(Student student) {
        return student_educatorRepository.findByStudent(student);
    }

    @Transactional
    public List<Student_Educator> findAllByEducator(Educator educator) {
        return student_educatorRepository.findAllByEducator(educator);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByUsername(name);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

}
