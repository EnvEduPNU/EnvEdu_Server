package com.example.demo.jpa.eclass.service;

import com.example.demo.jpa.eclass.repository.EClassSessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EClassSessionService {

    private final EClassSessionsRepository eClassSessionsRepository;

    public Optional<String> getSessionByEclassuuidAndUsername(String eclassUuid, String userName) {
        return eClassSessionsRepository.findSessionIdByEclassUuidAndUserName(eclassUuid, userName);
    }
}
