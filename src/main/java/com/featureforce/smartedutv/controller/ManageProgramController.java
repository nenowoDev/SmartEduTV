package com.featureforce.smartedutv.controller;

import com.featureforce.smartedutv.entity.ManageProgram;
import com.featureforce.smartedutv.service.ManageProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
public class ManageProgramController {

    @Autowired
    private ManageProgramService programService;

    // 1. RETRIEVE ALL or SEARCH (GET /api/programs or /api/programs?name=Math)
    // Postman: GET http://localhost:8080/api/programs
    // Postman: GET http://localhost:8080/api/programs?name=Math
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPrograms(
            @RequestParam(value = "name", required = false) String name) {

        List<ManageProgram> programs;
        String message;

        if (name != null && !name.trim().isEmpty()) {
            programs = programService.searchProgramsByName(name);
            message = programs.isEmpty()
                    ? "No programs found matching: " + name
                    : programs.size() + " program(s) found matching: " + name;
        } else {
            programs = programService.getAllPrograms();
            message = programs.isEmpty()
                    ? "No programs available"
                    : programs.size() + " program(s) retrieved successfully";
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", message);
        response.put("count", programs.size());
        response.put("data", programs);
        return ResponseEntity.ok(response);
    }

    // 2. RETRIEVE BY ID (GET /api/programs/{id})
    // Postman: GET http://localhost:8080/api/programs/1
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProgramById(@PathVariable("id") int id) {
        ManageProgram program = programService.getProgramById(id);

        Map<String, Object> response = new LinkedHashMap<>();
        if (program == null) {
            response.put("status", "error");
            response.put("message", "Program not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("status", "success");
        response.put("message", "Program retrieved successfully");
        response.put("data", program);
        return ResponseEntity.ok(response);
    }

    // 3. CREATE (POST /api/programs)
    // Postman: POST http://localhost:8080/api/programs
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProgram(@RequestBody ManageProgram program) {
        programService.saveProgram(program);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "Program created successfully");
        response.put("data", program);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 4. UPDATE (PUT /api/programs/{id})
    // Postman: PUT http://localhost:8080/api/programs/1
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProgram(@PathVariable("id") int id,
            @RequestBody ManageProgram updatedProgram) {

        ManageProgram existingProgram = programService.getProgramById(id);

        Map<String, Object> response = new LinkedHashMap<>();
        if (existingProgram == null) {
            response.put("status", "error");
            response.put("message", "Program not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        existingProgram.setProgramName(updatedProgram.getProgramName());
        existingProgram.setProgramDate(updatedProgram.getProgramDate());
        existingProgram.setProgramLocation(updatedProgram.getProgramLocation());
        existingProgram.setTargetParticipation(updatedProgram.getTargetParticipation());
        existingProgram.setProgramDescription(updatedProgram.getProgramDescription());
        existingProgram.setStatus(updatedProgram.getStatus());
        existingProgram.setHasReport(updatedProgram.isHasReport());
        programService.updateProgram(existingProgram);

        response.put("status", "success");
        response.put("message", "Program updated successfully");
        response.put("data", existingProgram);
        return ResponseEntity.ok(response);
    }

    // 5. DELETE (DELETE /api/programs/{id})
    // Postman: DELETE http://localhost:8080/api/programs/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProgram(@PathVariable("id") int id) {
        ManageProgram existingProgram = programService.getProgramById(id);

        Map<String, Object> response = new LinkedHashMap<>();
        if (existingProgram == null) {
            response.put("status", "error");
            response.put("message", "Program not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        programService.deleteProgram(id);
        response.put("status", "success");
        response.put("message", "Program deleted successfully");
        response.put("deletedId", id);
        return ResponseEntity.ok(response);
    }
}
