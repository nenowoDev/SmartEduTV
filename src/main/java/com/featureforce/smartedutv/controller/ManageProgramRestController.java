package com.featureforce.smartedutv.controller;

import com.featureforce.smartedutv.entity.ManageProgram;
import com.featureforce.smartedutv.service.ManageProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs") // Unified API prefix
public class ManageProgramRestController {

    @Autowired
    private ManageProgramService programService;

    // FUNCTION 1: Fetch all programs (GET)
    // Postman: GET http://localhost:8080/api/programs
    @GetMapping
    public List<ManageProgram> getAllPrograms() {
        return programService.getAllPrograms();
    }

    // FUNCTION 2: Create a new program (POST)
    // Postman: POST http://localhost:8080/api/programs
    @PostMapping
    public ManageProgram createProgram(@RequestBody ManageProgram program) {
        programService.saveProgram(program);
        return program;
    }
}
