package com.java_web.demo.Repository;

import com.java_web.demo.Model.Task;

import java.util.List;

public interface TaskRepository {
    
    Task save(Task newTask);
    Task findById(Long id);
    List<Task> findAllByProjectId(Long id);
    Task update(Task newTask);
    void delete(Long id);

}
