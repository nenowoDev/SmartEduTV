package com.featureforce.smartedutv.repository;

import com.featureforce.smartedutv.entity.ManageProgram;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ManageProgramDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    // 1. ADD
    public void saveProgram(ManageProgram program) {
        getSession().persist(program);
    }

    // 2. UPDATE
    public void updateProgram(ManageProgram program) {
        getSession().merge(program);
    }

    // 3. DELETE
    public void deleteProgram(int id) {
        ManageProgram program = entityManager.find(ManageProgram.class, id);
        if (program != null) {
            getSession().remove(program);
        }
    }

    // 4. GET ALL
    public List<ManageProgram> getAllPrograms() {
        return getSession().createQuery("from ManageProgram", ManageProgram.class).getResultList();
    }

    // 5. SEARCH (Find by specific ID helper)
    @Transactional(readOnly = true)
    public ManageProgram getProgramById(int id) {
        return entityManager.find(ManageProgram.class, id);
    }

    // 6. SEARCH BY NAME (5th functionality)
    @Transactional(readOnly = true)
    public List<ManageProgram> searchProgramsByName(String name) {
        return getSession().createQuery("from ManageProgram where lower(programName) like :name", ManageProgram.class)
                           .setParameter("name", "%" + name.toLowerCase() + "%")
                           .getResultList();
    }
}
