package com.example.demo.datacontrol.datachunk.controller;

import com.example.demo.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class DataChunkController {
    private final DataChunkService dataChunkService;
    @GetMapping("/mydata/list")
    public ResponseEntity<?> myDataComp(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        return new ResponseEntity<>(dataChunkService.findMyDataCompilation(userInfo.get(JwtUtil.claimUsername).toString()), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
