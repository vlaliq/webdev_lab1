package com.java_web.demo.Controller;

import com.java_web.demo.Model.Project;
import com.java_web.demo.Service.ProjectService;
import com.java_web.demo.dto.ProjectDTO;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO newProject) {
        return new ResponseEntity<Project>(projectService.createProject(newProject), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.readProject(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectDTO newProject) {
        return ResponseEntity.ok(projectService.updateProject(id, newProject));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getFilter(@RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                   @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(projectService.readFilter(startDate, endDate));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
    //    jdbcTemplate.queryForRowSet("SELECT * FROM project LEFT OUTER JOIN task ON project.id = task.projectId;");
        return ResponseEntity.ok(projectService.readAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }





    // private final ProjectService projectService;

    // @Autowired
    // public ProjectController(ProjectService projectService) {
    //     this.projectService = projectService;
    // }

    // @PostMapping
    // public ResponseEntity<Project> createProject(@RequestBody Project project) {
    //     Project createdProject = projectService.createProject(project);
    //     return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    // }

    // @PutMapping("/{projectId}")
    // public ResponseEntity<Void> updateProject(@PathVariable Long projectId, @RequestBody Project project) {
    //     project.setId(projectId);
    //     projectService.updateProject(project);
    //     return ResponseEntity.ok().build();
    // }

    // @DeleteMapping("/{projectId}")
    // public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
    //     projectService.deleteProject(projectId);
    //     return ResponseEntity.noContent().build();
    // }

    // @GetMapping("/{projectId}")
    // public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
    //     Project project = projectService.getProjectById(projectId);
    //     return ResponseEntity.ok(project);
    // }

    // @GetMapping
    // public ResponseEntity<List<Project>> getAllProjects() {
    //     List<Project> projects = projectService.getAllProjects();
    //     return ResponseEntity.ok(projects);
    // }

    // @GetMapping("/all")
    // public ResponseEntity<List<Project>> getProjectsByDateRange(@RequestParam String start_date, @RequestParam String end_date) {
    //     List<Project> projects = projectService.getProjectsByDateRange(start_date, end_date);
    //     return ResponseEntity.ok(projects);
    // }
}
