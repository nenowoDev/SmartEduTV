package com.featureforce.smartedutv.controller;

import com.featureforce.smartedutv.entity.ManageProgram;
import com.featureforce.smartedutv.service.ManageProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs") // Unified API prefix
public class ManageProgramRestController {

    @Autowired
    private ManageProgramService programService;

    // 1. RETRIEVE ALL or SEARCH (GET /api/programs or /api/programs?name=Math)
    // Postman: GET http://localhost:8080/api/programs?name=Math
    @GetMapping
    public List<ManageProgram> getPrograms(@RequestParam(value = "name", required = false) String name) {
        if (name != null && !name.trim().isEmpty()) {
            return programService.searchProgramsByName(name);
        }
        return programService.getAllPrograms();
    }

    // 2. RETRIEVE BY ID (GET /api/programs/{id})
    // Postman: GET http://localhost:8080/api/programs/1
    @GetMapping("/{id}")
    public ResponseEntity<ManageProgram> getProgramById(@PathVariable("id") int id) {
        ManageProgram program = programService.getProgramById(id);
        if (program == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(program);
    }

    // 3. CREATE (POST /api/programs)
    // Postman: POST http://localhost:8080/api/programs
    @PostMapping
    public ManageProgram createProgram(@RequestBody ManageProgram program) {
        programService.saveProgram(program);
        return program;
    }

    // 4. UPDATE (PUT /api/programs/{id})
    // Postman: PUT http://localhost:8080/api/programs/1
    @PutMapping("/{id}")
    public ResponseEntity<ManageProgram> updateProgram(@PathVariable("id") int id,
            @RequestBody ManageProgram updatedProgram) {
        ManageProgram existingProgram = programService.getProgramById(id);
        if (existingProgram == null) {
            return ResponseEntity.notFound().build();
        }
        // Update fields
        existingProgram.setProgramName(updatedProgram.getProgramName());
        existingProgram.setProgramDate(updatedProgram.getProgramDate());
        existingProgram.setProgramLocation(updatedProgram.getProgramLocation());
        existingProgram.setTargetParticipation(updatedProgram.getTargetParticipation());
        existingProgram.setProgramDescription(updatedProgram.getProgramDescription());
        existingProgram.setStatus(updatedProgram.getStatus());
        existingProgram.setHasReport(updatedProgram.isHasReport());

        programService.updateProgram(existingProgram);
        return ResponseEntity.ok(existingProgram);
    }

    // 5. DELETE (DELETE /api/programs/{id})
    // Postman: DELETE http://localhost:8080/api/programs/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") int id) {
        ManageProgram existingProgram = programService.getProgramById(id);
        if (existingProgram == null) {
            return ResponseEntity.notFound().build();
        }
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
}
