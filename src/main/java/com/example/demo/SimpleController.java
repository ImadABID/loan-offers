package com.example.demo;

import com.example.demo.db.DataEntity;
import com.example.demo.db.DataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
public class SimpleController {

    private DataRepository dataRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SimpleController(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }
    
    @GetMapping("/loans")
    public ResponseEntity<String> getLoans() {

        Throwable throwable;
        String errorMessage;

        try {

            List<DataEntity> data = dataRepository.findAll();
            String jsonString = objectMapper.writeValueAsString(data);
            return ResponseEntity
                    .ok()
                    .body(jsonString);

        } catch (JsonProcessingException e) {
            errorMessage = "error while transforming data to json";
            throwable = e;
        } catch (Exception e){
            errorMessage = "error while retreiving data from the database";
            throwable = e;
        }

        log.error(errorMessage, throwable);
        return ResponseEntity.internalServerError().body(errorMessage);

    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<DataEntity> getLoan(@PathVariable Long id) {

        Optional<DataEntity> optionalData = dataRepository.findById(id);

        if(optionalData.isPresent()){
            DataEntity data = optionalData.get();
            return ResponseEntity
                .ok()
                .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
    

    @PostMapping("/loan")
    public ResponseEntity<String> postLoan(@RequestBody String data) {

        Throwable throwable;
        String errorMessage;

        DataEntity dataEntity = new DataEntity(data);

        try {

            DataEntity resDataEntity = dataRepository.save(dataEntity);
            String jsonString = objectMapper.writeValueAsString(resDataEntity);
            return ResponseEntity
                    .created(URI.create(String.format("http://localhost/%d", resDataEntity.getId())))
                    .body(jsonString);

        } catch (JsonProcessingException e) {
            errorMessage = "error while transforming data to json";
            throwable = e;
        } catch (Exception e){
            errorMessage = "error while storing data in the database";
            throwable = e;
        }
        
        log.error(errorMessage, throwable);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    @PutMapping("/loan/{id}")
    public ResponseEntity<String> putLoan(@PathVariable Long id, @RequestBody String data) {

        Throwable throwable;
        String errorMessage;

        DataEntity dataEntity = new DataEntity(id, data);

        try {

            DataEntity resDataEntity = dataRepository.save(dataEntity);
            String jsonString = objectMapper.writeValueAsString(resDataEntity);
            return ResponseEntity
                    .accepted()
                    .body(jsonString);

        } catch (JsonProcessingException e) {
            errorMessage = "error while transforming data to json";
            throwable = e;
        } catch (Exception e){
            errorMessage = "error while modifying data in the database";
            throwable = e;
        }
        
        log.error(errorMessage, throwable);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    @DeleteMapping("/loan/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id) {

        Throwable throwable;
        String errorMessage;

        try {
            dataRepository.deleteById(id);
            return ResponseEntity
                    .ok().body(null);
        }catch (Exception e){
            errorMessage = "error while deleting data from the database";
            throwable = e;
        }
        
        log.error(errorMessage, throwable);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    @GetMapping("csrf")
    public ResponseEntity<CsrfToken> loanPost(CsrfToken csrfToken) {
        return ResponseEntity.ok().body(csrfToken);
    }

}
