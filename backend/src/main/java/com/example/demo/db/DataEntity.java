package com.example.demo.db;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mytab")
public class DataEntity {

    public DataEntity(String data){
        this.strdata = data;
        this.addTime = new Date(System.currentTimeMillis());
    }

    public DataEntity(Long id, String data){
        this.id = id;
        this.strdata = data;
        this.addTime = new Date(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String strdata;

    @Column(name = "add_time")
    private Date addTime;

}
