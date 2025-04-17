package com.example.demo;

import com.example.demo.db.DataEntity;
import com.example.demo.db.DataRepository;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
public class LoanController {

    private DataRepository dataRepository;

    public LoanController(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }
    
    @GetMapping("/loans")
    public ResponseEntity<List<DataEntity>> getLoans() {

        List<DataEntity> data = dataRepository.findAll();
        return ResponseEntity
                .ok()
                .body(data);

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
    public ResponseEntity<DataEntity> postLoan(@RequestBody String data) {

        DataEntity dataEntity = new DataEntity(data);
        DataEntity resDataEntity = dataRepository.save(dataEntity);
        return ResponseEntity
                    .created(URI.create(String.format("http://localhost/loan/%d", resDataEntity.getId())))
                    .body(resDataEntity);
    }

    @PutMapping("/loan/{id}")
    public ResponseEntity<DataEntity> putLoan(@PathVariable Long id, @RequestBody String data) {

        DataEntity dataEntity = new DataEntity(id, data);
        DataEntity resDataEntity = dataRepository.save(dataEntity);
        return ResponseEntity
                    .accepted()
                    .body(resDataEntity);

    }

    @DeleteMapping("/loan/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {

        dataRepository.deleteById(id);
        return ResponseEntity
            .ok().body(null);

    }

    @GetMapping("csrf")
    public ResponseEntity<CsrfToken> loanPost(CsrfToken csrfToken) {
        return ResponseEntity.ok().body(csrfToken);
    }

}
