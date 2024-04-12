package com.java_web.demo.Repository.RepositoryImpl;

import com.java_web.demo.Model.Project;
import com.java_web.demo.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository

public class ProjectRepositoryImpl implements ProjectRepository{
    
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Project> rm = (resultSet, rowNum) -> {
        Project project = Project.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .startDate(resultSet.getDate("startDate"))
                .endDate(resultSet.getDate("endDate"))
                .build();
        return project;
    };
    @Override
    public Project save(Project newProject) {
        generateId(newProject);
        jdbcTemplate.update("INSERT into project (id, name, description, startDate, endDate) VALUES (?, ?, ?, ?, ?)",
                newProject.getId(),
                newProject.getName(), newProject.getDescription(), newProject.getStartDate(),
                newProject.getEndDate());
        return newProject;
    }

    @Override
    public Project findById(Long id) {
        return jdbcTemplate.queryForObject("select * from project where id = ?", rm, id);
    }

    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query("select * from project", rm);
    }

    @Override
    public List<Project> filter(Date startDate, Date endDate) {
        return jdbcTemplate.query("select * from project where startDate >= ? and endDate <= ?",
                rm, startDate, endDate);
    }

    @Override
    public Project update(Project newProject) {
        jdbcTemplate.update("update project set name=?, description=?, startDate=?, endDate=? where id=?",
                newProject.getName(), newProject.getDescription(), newProject.getStartDate(), newProject.getEndDate(), newProject.getId());
        return newProject;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from project where id=?", id);
    }

    private Project generateId(Project project){
        if (project.getId() == null) {
            Long id = jdbcTemplate.query("SELECT nextval('project_sequence')",
                    rs -> {
                        if (rs.next()) {
                            return rs.getLong(1);
                        } else {
                            throw new SQLException();
                        }
                    });
            project.setId(id);
        }

        return project;
    }


}
