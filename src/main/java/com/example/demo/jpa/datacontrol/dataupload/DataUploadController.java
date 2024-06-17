package com.example.demo.jpa.datacontrol.dataupload;

import com.example.demo.jpa.datacontrol.dataupload.dto.DataUploadRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DataUploadController {
    private final DataUploadService dataUploadService;
    @PostMapping("/dataupload")
    public ResponseEntity<?> uploadExcel(@RequestBody DataUploadRequestDto uploadedData, HttpServletRequest request) {

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        dataUploadService.uploadData(uploadedData, userName);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
