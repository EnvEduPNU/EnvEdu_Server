package com.example.demo.device.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddMACDTO {
    private List<String> macs = new ArrayList<>();
}
