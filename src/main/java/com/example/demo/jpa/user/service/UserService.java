package com.example.demo.jpa.user.service;

import com.example.demo.jpa.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.jpa.exceptions.CustomMailException;
import com.example.demo.jpa.exceptions.DuplicateAttributeException;
//import com.example.demo.jpa.jwt.util.JwtUtil;
import com.example.demo.jpa.service.MailService;
import com.example.demo.jpa.redis.entity.AuthNum;
import com.example.demo.jpa.redis.repo.AuthNumRepository;
import com.example.demo.jpa.user.dto.request.EmailDTO;
import com.example.demo.jpa.user.dto.request.RegisterDTO;
import com.example.demo.jpa.user.dto.request.StudentAddDTO;
import com.example.demo.jpa.user.dto.response.Student_EducatorDTO;
import com.example.demo.jpa.user.model.entity.*;
import com.example.demo.jpa.user.repository.*;
import com.example.demo.jpa.user.model.enumerate.State;
import com.example.demo.jpa.user.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final Student_EducatorRepository student_educatorRepository;


    /**
     register service
     */
//    @Transactional
//    public void addUser(RegisterDTO registerDTO) {
//        User user = registerDTO.getRole().generateUserByRole(registerDTO, bCryptPasswordEncoder);
//        if(userRepository.existsByUsernameAndState(user.getUsername(), State.ACTIVE)) {
//            throw new DuplicateAttributeException("아이디");
//        }
//        userRepository.save(user);
//    }


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

}
