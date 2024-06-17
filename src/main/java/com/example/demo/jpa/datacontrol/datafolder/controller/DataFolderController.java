package com.example.demo.jpa.datacontrol.datafolder.controller;

import com.example.demo.jpa.datacontrol.datafolder.dto.DataFolderDto;
import com.example.demo.jpa.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.jpa.datacontrol.datafolder.dto.DataFromDataFolderDto;
import com.example.demo.jpa.datacontrol.datafolder.dto.DataToDataFolderDto;
import com.example.demo.jpa.datacontrol.datafolder.service.DataFolderService;
import com.example.demo.jpa.datacontrol.datafolder.model.DataFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DataFolderController {

    private final DataFolderService dataFolderService;

    @DeleteMapping("/datafolder/item/delete")
    public ResponseEntity<?> deleteDataFromDataFolder(@RequestBody DataFromDataFolderDto data){
        dataFolderService.deleteDataFolderItem(data.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/datafolder/item/store")
    public ResponseEntity<?> storeDataToDataFolder(@RequestBody DataToDataFolderDto dataToDataFolderDto){
        // warn : 한번의 요청에 같은 타입만 넣어야 함
        dataFolderService.store(dataToDataFolderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/datafolder/list/foldername")
    public ResponseEntity<?> updateDataFolderName(HttpServletRequest request, @RequestBody DataFolderDto dataFolderDto){

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        dataFolderService.updateDataFolderName(userName, dataFolderDto);
        //dataFolderService.updateDataFolderName("Student1", dataFolderDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/datafolder/list")
    public ResponseEntity<?> deleteDataFolder(HttpServletRequest request, @RequestBody DataFolderDto dataFolderDto){

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        dataFolderService.deleteDataFolder(userName, dataFolderDto);
        //dataFolderService.deleteDataFolder("Student1", dataFolderDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/datafolder/list")
    public ResponseEntity<?> parentStoreDataFolder(HttpServletRequest request, @RequestBody DataFolderDto dataFolderDto){
        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        dataFolderService.linkParentDataFolder(userName, dataFolderDto);
        //dataFolderService.linkParentDataFolder("Student1", dataFolderDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/datafolder/list")
    public ResponseEntity<?> postDataFolder(HttpServletRequest request, @RequestBody DataFolderDto dataFolderDto) {

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        dataFolderService.createDataFolder(userName, dataFolderDto);
        //dataFolderService.createDataFolder("Student1", dataFolderDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/datafolder/items")
    public ResponseEntity<?> getDataFolderCompilation(@RequestParam Long id){
        DataFolder_DataCompilationDto byDataFolder = dataFolderService.findByDataFolderCompilationId(id);

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
    @GetMapping("/datafolder/list")
    public ResponseEntity<?> getDataFolder(HttpServletRequest request){

        String userName = String.valueOf(request.getHeader("userName"));
        log.info("Username : " + userName);

        List<DataFolder> byDataFolder = dataFolderService.findDataFolderList(userName);
        //List<DataFolder> byDataFolder = dataFolderService.findDataFolderList("Student1");

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<?> noSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("저장된 데이터 값이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
