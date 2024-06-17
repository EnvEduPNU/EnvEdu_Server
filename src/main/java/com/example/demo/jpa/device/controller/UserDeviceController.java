package com.example.demo.jpa.device.controller;

import com.example.demo.jpa.device.dto.request.AddMACDTO;
import com.example.demo.jpa.device.dto.request.DeviceUpdateDTO;
import com.example.demo.jpa.device.dto.response.RelatedUserDeviceListDTO;
import com.example.demo.jpa.device.service.UserDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserDeviceController {
    private final UserDeviceService userDeviceService;

    @PostMapping("/admin/device")
    private ResponseEntity<?> addDevice(@RequestBody AddMACDTO addMACDTO) {
        userDeviceService.addDevice(addMACDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/devices")
    private ResponseEntity<?> getAllDevices() {
        return new ResponseEntity<>(userDeviceService.getAllDevices(), HttpStatus.OK);
    }

    @PutMapping("/admin/device")
    private ResponseEntity<?> updateDevice(@RequestBody DeviceUpdateDTO deviceUpdateDTO) {
        userDeviceService.updateDevice(deviceUpdateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/device/{mac}")
    private ResponseEntity<?> deleteDevice(@PathVariable String mac) {
        userDeviceService.deleteDevice(mac);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 자신과 연관된 기기 목록 조회
     * 학생의 경우, 자신에게 등록된 기기
     * 교사의 경우, 자신의 기기 및 자신이 지도하는 학생에게 등록된 기기
     */
    @GetMapping("/seed/device")
    private ResponseEntity<?> getMyDevices(HttpServletRequest request) {
        log.info("seed/device 접근");

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        RelatedUserDeviceListDTO check = userDeviceService.getDeviceList(userName);
        log.info("RelatedUserDeviceListDTO : " + check.toString());

        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
