package com.example.demo.jpa.user.repository;

import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.model.enumerate.Role;
import com.example.demo.jpa.user.model.enumerate.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameAndState(String username, State state);
    boolean existsByEmailAndState(String email, State state);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);
    Optional<User> findByUsernameAndState(String username, State state);
    Optional<List<User>> findByIdIn(List<Long> id);

    Optional<User> findBySessionId(long id);

    List<User> findByRole(Role role);

    @Query("SELECT s.id FROM Student s WHERE s.username = :username")
    Long findIdByUsername(@Param("username") String username);

}
