package com.featureforce.smartedutv.controller;

import com.featureforce.smartedutv.entity.ManageProgram;
import com.featureforce.smartedutv.service.ManageProgramService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

// Main MVC controller handling original web page UI renders
@Controller
@RequestMapping("/program")
public class ManageProgramController {

    @Autowired
    private ManageProgramService programService;

    // 1. Path for the teacher program plan page (UI rendering)
    @GetMapping("/teacher/manageProgramPlan")
    @PreAuthorize("hasRole('TEACHER')")
    public String manageProgram(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        List<ManageProgram> allPrograms = programService.getAllPrograms();

        if (allPrograms.isEmpty()) {
            model.addAttribute("programs", allPrograms);
            model.addAttribute("page", 1);
            model.addAttribute("totalPages", 1);
            return "ProgramModule/manageProgramPlan";
        }

        int totalPrograms = allPrograms.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalPrograms / pageSize));

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int startIndex = (page - 1) * pageSize;
        if (startIndex >= totalPrograms) {
            startIndex = 0;
            page = 1;
        }

        int endIndex = Math.min(startIndex + pageSize, totalPrograms);
        List<ManageProgram> paginatedPrograms = allPrograms.subList(startIndex, endIndex);

        model.addAttribute("programs", paginatedPrograms);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);

        return "ProgramModule/manageProgramPlan";
    }

    // 2. Path for teachers to view add form
    @GetMapping("/teacher/manage/addProgram")
    @PreAuthorize("hasRole('TEACHER')")
    public String showAddProgramForm(Model model) {
        model.addAttribute("program", new ManageProgram());
        return "ProgramModule/addProgram";
    }

    // 3. Handle form submission for adding a new program
    @PostMapping("/teacher/manage/addProgram")
    @PreAuthorize("hasRole('TEACHER')")
    public String addProgram(@ModelAttribute ManageProgram program, Model model) {
        if (program.getProgramName() == null || program.getProgramName().trim().isEmpty() ||
                program.getProgramDate() == null ||
                program.getProgramLocation() == null || program.getProgramLocation().trim().isEmpty() ||
                program.getTargetParticipation() == null || program.getTargetParticipation() < 1 ||
                program.getProgramDescription() == null || program.getProgramDescription().trim().isEmpty()) {

            model.addAttribute("error", "Please fill out this field.");
            return "ProgramModule/addProgram";
        }

        programService.saveProgram(program);
        return "redirect:/program/teacher/manageProgramPlan";
    }

    // 4. Show form to edit a Program
    @GetMapping("/teacher/manage/editProgram/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String showEditProgramForm(@PathVariable("id") int id, Model model) {
        ManageProgram program = programService.getProgramById(id);

        if (program == null || "Approved".equals(program.getStatus()))
            return "redirect:/program/teacher/manageProgramPlan";

        model.addAttribute("program", program);
        return "ProgramModule/editProgram";
    }

    // 5. Update Program processing
    @PostMapping("/teacher/manage/updateProgram")
    @PreAuthorize("hasRole('TEACHER')")
    public String updateProgram(@ModelAttribute ManageProgram program) {
        if ("Rejected".equals(program.getStatus())) {
            program.setStatus("Pending");
        }
        programService.updateProgram(program);
        return "redirect:/program/teacher/manageProgramPlan";
    }

    // 6. Delete a program
    @PostMapping("/teacher/manage/deleteProgram")
    @PreAuthorize("hasRole('TEACHER')")
    public String deleteProgram(@ModelAttribute ManageProgram program) {
        try {
            programService.deleteProgram(program.getId());
            return "redirect:/program/teacher/manageProgramPlan";
        } catch (Exception e) {
            return "redirect:/program/teacher/manageProgramPlan?error=Failed to delete program: " + e.getMessage();
        }
    }

    // 7. Show program deletion confirmation dialog UI
    @GetMapping("/teacher/manage/deleteProgram/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String showDeleteProgramForm(@PathVariable("id") int id, Model model) {
        ManageProgram program = programService.getProgramById(id);
        if (program == null) {
            return "redirect:/program/teacher/manageProgramPlan";
        }
        model.addAttribute("program", program);
        return "ProgramModule/deleteProgram";
    }

    // 8. Path for the admin program plan page
    @GetMapping("/admin/manageProgram")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewPrograms(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        List<ManageProgram> allPrograms = programService.getAllPrograms();

        int totalPrograms = allPrograms.size();
        int totalPages = (int) Math.ceil((double) totalPrograms / pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalPrograms);
        List<ManageProgram> paginatedPrograms = allPrograms.subList(startIndex, endIndex);

        model.addAttribute("programs", paginatedPrograms);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);

        return "ProgramModule/manageProgram";
    }

    // 9. Admin view details layout
    @GetMapping("/admin/view/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewProgram(@PathVariable("id") int id, Model model) {
        ManageProgram program = programService.getProgramById(id);
        if (program == null) {
            return "redirect:/program/admin/manageProgram";
        }
        model.addAttribute("program", program);
        return "ProgramModule/viewProgram";
    }
}
