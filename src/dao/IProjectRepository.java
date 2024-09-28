package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import java.util.List;

public interface IProjectRepository {
    boolean createEmployee(Employee emp) throws ProjectNotFoundException;
    boolean createProject(Project pj);
    boolean createTask(Task task) throws EmployeeNotFoundException, ProjectNotFoundException; // Updated to throw exceptions
    boolean assignProjectToEmployee(int projectId, int employeeId) throws EmployeeNotFoundException, ProjectNotFoundException;
    boolean assignTaskToEmployee(int taskId, int projectId, int employeeId) throws EmployeeNotFoundException, ProjectNotFoundException;
    boolean deleteEmployee(int userId) throws EmployeeNotFoundException;
    boolean deleteProject(int projectId) throws ProjectNotFoundException;
    List<Task> getAllTasks(int empId, int projectId);
}
