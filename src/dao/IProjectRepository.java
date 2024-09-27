package dao;

import entity.Employee;
import entity.Project;
import entity.Task;

import java.util.List;

public interface IProjectRepository {
    boolean createEmployee(Employee emp);
    boolean createProject(Project pj);
    boolean createTask(Task task);
    boolean assignProjectToEmployee(int projectId, int employeeId);
    boolean assignTaskToEmployee(int taskId, int projectId, int employeeId);
    boolean deleteEmployee(int userId);
    boolean deleteProject(int projectId);
    List<Task> getAllTasks(int empId, int projectId);
}
