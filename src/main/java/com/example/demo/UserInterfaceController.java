package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.db.DataEntity;
import com.example.demo.db.DataRepository;

@Controller
public class UserInterfaceController {

    private DataRepository dataRepository;

    public UserInterfaceController(DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }
    
    @GetMapping("loanpost")
    public String loanPost(Model model) {
        List<DataEntity> data = dataRepository.findAll();
        model.addAttribute("loans", data);
        return "loanPost";
    }
    
}

