package com.java_web.demo.Repository;
import com.java_web.demo.Model.Project;

import java.util.Date;
import java.util.List;

public interface ProjectRepository {
    
    Project save(Project newProject);
    Project findById(Long id);
    List<Project> findAll();
    List<Project> filter(Date startDate, Date endDate);
    Project update(Project newProject);
    void delete(Long id);
    
}
