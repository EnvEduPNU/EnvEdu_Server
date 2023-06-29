package com.example.demo.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StudentAddDTO {
    private List<String> studentUsernames = new ArrayList<>();

    @Override
    public String toString() {
        return studentUsernames.toString();
    }
}
