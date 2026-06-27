package com.featureforce.smartedutv.service;

import com.featureforce.smartedutv.entity.ManageProgram;
import com.featureforce.smartedutv.repository.ManageProgramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ManageProgramService {

    @Autowired
    private ManageProgramDao programDao;

    public List<ManageProgram> getAllPrograms() {
        return programDao.getAllPrograms();
    }

    public ManageProgram getProgramById(int id) {
        return programDao.getProgramById(id);
    }

    public void saveProgram(ManageProgram program) {
        programDao.saveProgram(program);
    }

    public void updateProgram(ManageProgram program) {
        if ("Rejected".equals(program.getStatus())) {
            program.setStatus("Pending");
        }
        programDao.updateProgram(program);
    }

    public void deleteProgram(int id) {
        programDao.deleteProgram(id);
    }

    public List<ManageProgram> searchProgramsByName(String name) {
        return programDao.searchProgramsByName(name);
    }
}
