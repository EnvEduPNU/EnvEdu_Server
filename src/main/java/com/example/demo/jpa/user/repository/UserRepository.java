package com.example.demo.jpa.user.repository;

import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.model.enumerate.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);

    Optional<List<User>> findByIdIn(List<Long> id);

    Optional<User> findBySessionId(long id);

    List<User> findByRole(String role);

    @Query("SELECT s.id FROM Student s WHERE s.username = :username")
    Long findIdByUsername(@Param("username") String username);

    boolean existsByEmail(@Pattern(regexp = "^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$", message = "잘못된 형식의 이메일입니다") String email);
}
