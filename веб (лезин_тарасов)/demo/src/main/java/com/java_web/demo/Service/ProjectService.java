package com.java_web.demo.Service;
import com.java_web.demo.Exception.NotFoundException;
import com.java_web.demo.Model.Project;
import com.java_web.demo.Model.Task;
import com.java_web.demo.Repository.ProjectRepository;
import com.java_web.demo.dto.ProjectDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public Project createProject(ProjectDTO newProject) {
        /*if(newProject.getStartDate().after(newProject.getEndDate())) {
            throw new RuntimeException();
        }*/

        return projectRepository.save(Project.builder()
        .id(newProject.getId())
        .name(newProject.getName())
        .description(newProject.getDescription())
        .startDate(newProject.getStartDate())
        .endDate(newProject.getEndDate())
        .build());
    }

    public Project readProject(Long id) throws NotFoundException {
        try {
            Project rp = projectRepository.findById(id);
            List<Task> tasks = taskService.readAllTasksByProject(id);
            rp.setTasks(tasks);
            return rp;
        }
       catch (DataAccessException e) {
            throw new NotFoundException();
       }
    }

    public List<Project> readAll() {
        List<Project> projects = projectRepository.findAll();
        List<Project> newProjects = new ArrayList<>();
        for(int i=0; i<projects.size(); i++) {
            newProjects.add(readProject(projects.get(i).getId()));
        }
        return newProjects;
    }

    public List<Project> readFilter(Date startDate, Date endDate) {
        /*if (startDate.after(endDate)) {
            throw new RuntimeException();
        }*/
        List<Project> projects = projectRepository.filter(startDate, endDate);
        List<Project> newProjects = new ArrayList<>();

        for(int i=0; i<projects.size(); i++) {
            newProjects.add(readProject(projects.get(i).getId()));
        }
        return newProjects;
        //  return projectRepository.filter(startDate, endDate);
    }

    public Project updateProject(Long id, ProjectDTO newProject) throws NotFoundException {
        try {
            newProject.setId(id);
            // НЕ УДАЛЯТЬ
            // List<Task> newTasks = newProject.getTasks();
            // for (int i = 0; i < newTasks.size(); i++) {
            //     taskService.updateTask(newTasks.get(i));
            // }
            return projectRepository.update(Project.builder()
                .name(newProject.getName())
                .description(newProject.getDescription())
                .startDate(newProject.getStartDate())
                .endDate(newProject.getEndDate())
                .build());
        }
        catch(DataAccessException e) {
            throw new NotFoundException();
        }
    }

    public void deleteProject(Long id) {
        Project p = projectRepository.findById(id);
        List<Task> tasks = p.getTasks();
        if(tasks!=null) {
            for (int i = 0; i < tasks.size(); i++) {
                taskService.deleteTask(tasks.get(i).getId());
            }
        }
        projectRepository.delete(id);
    }



    

    // private final ProjectRepositoryImpl projectRepository;

    // @Autowired
    // public ProjectService(ProjectRepositoryImpl projectRepository) {
    //     this.projectRepository = projectRepository;
    // }

    // public Project createProject(Project project) {
    //     projectRepository.create(project);
    //     return project;
    // }

    // public Project getProjectById(Long id){
    //     return projectRepository.findById(id);
    // }

    // public void updateProject(Project project){
    //     projectRepository.update(project);
    // }

    // public void deleteProject(Long id){
    //     projectRepository.delete(id);
    // }
    
    // public List<Project> getAllProjects(){
    //     return projectRepository.findAll()
    // }
    
    // public List<Project> getProjectsByDateRange(LocalDate begin, LocalDate end){
    //     return projectRepository.findByDateRange(begin, end);
    // }

    // _______________________________________________________________________________________


    // private final ProjectRepository projectRepository;

    // //    Создание проекта.
    // //    POST /projects
    // //    Должен вернуть 201 код в случае успешного создания проекта, а также сущность созданного проекта.
    // public Optional<Dto> create(
    //         String name,
    //         String description,
    //         LocalDate begin,
    //         LocalDate end
    // ) {
    //     var dbo = projectRepository.create( name,  description,  begin,  end);
    //     if (dbo.isPresent())
    //         return Optional.of(
    //                 new Dto(
    //                         dbo.get().getId(),
    //                         dbo.get().getName(),
    //                         dbo.get().getDescription(),
    //                         dbo.get().getBegin(),
    //                         dbo.get().getEnd()
    //                 )
    //         );
    //     return Optional.empty();
    // }

    // //    Модификация проекта
    // //    PUT /projects/{projectId}
    // //    Вернуть 200 код в случае успешной модификации проекта. Если проект с переданным ID не найден, то вернуть 404 код ответа.
    // public boolean setName(Long id, String name) {
    //     return projectRepository.setName( id,  name);
    // }

    // public boolean setDescription(Long id, String description) {
    //     return projectRepository.setDescription( id,  description);
    // }

    // public boolean setBegin(Long id, LocalDate begin) {
    //     return projectRepository.setBegin( id,  begin);
    // }

    // public boolean setEnd(Long id, LocalDate end) {
    //     return projectRepository.setEnd( id,  end);
    // }

    // //    Удаление проекта
    // //    DELETE /projects/{projectId}
    // //    Вернуть 204 код.
    // public boolean delete(Long id) {
    //     return projectRepository.delete( id);
    // }

    // //    Получение проекта
    // //    GET /projects/{projectId}
    // //    Вернуть 404 код ошибки если не найден.
    // public Optional<Dto> getById(Long id) {
    //     var dbo = projectRepository.getById(id);
    //     if (dbo.isPresent()) {
    //         var mod = dbo.get();
    //         var pojo = new Dto(mod.getId(), mod.getName(), mod.getDescription(), mod.getBegin(), mod.getEnd());
    //         return Optional.of(pojo);
    //     }
    //     return Optional.empty();
    // }

    // //    Получение проектов с фильтрацией по диапазону. Дата начала и дата окончания должна быть в переданном интервале
    // //    GET /projects?start_date={start_date}&end_date={end_date}
    // public Set<Dto> getByRange(LocalDate begin, LocalDate end) {
    //     return projectRepository.getByRange(
    //             begin, end
    //     ).stream().map(
    //                     (Project p) -> {
    //                         return new Dto(
    //                                 p.getId(),
    //                                 p.getName(),
    //                                 p.getDescription(),
    //                                 p.getBegin(),
    //                                 p.getEnd());
    //                     }
    //                     ).collect(Collectors.toSet());
    // }
}
