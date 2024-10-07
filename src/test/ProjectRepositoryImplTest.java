package test;

import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectRepositoryImplTest {

    private ProjectRepositoryImpl repo;

    @BeforeEach
    public void setUp() {
        // Initialize the mock object before each test
        repo = mock(ProjectRepositoryImpl.class);
    }

    @Test
    public void testCreateEmployee() throws ProjectNotFoundException {
        Employee emp = new Employee();
        emp.setName("John");
        emp.setDesignation("Developer");
        emp.setGender("Male");
        emp.setSalary(5000);
        emp.setProjectId(1);  // Ensure a valid project ID

        when(repo.createEmployee(emp)).thenReturn(true);

        boolean result = repo.createEmployee(emp);

        assertTrue(result, "Employee should be created successfully");
        verify(repo, times(1)).createEmployee(emp);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setProjectName("Project Alpha");
        project.setDescription("New software project");
        project.setStartDate(java.time.LocalDate.now());
        project.setStatus("Active");

        when(repo.createProject(project)).thenReturn(true);

        boolean result = repo.createProject(project);

        assertTrue(result, "Project should be created successfully");
        verify(repo, times(1)).createProject(project);
    }

    @Test
    public void testAssignProjectToEmployee() throws EmployeeNotFoundException, ProjectNotFoundException {
        int employeeId = 1;
        int projectId = 2;

        when(repo.checkProjectExists(projectId)).thenReturn(true);  // Mock project exists
        when(repo.checkEmployeeExists(employeeId)).thenReturn(true);  // Mock employee exists

        when(repo.assignProjectToEmployee(projectId, employeeId)).thenReturn(true);

        boolean result = repo.assignProjectToEmployee(projectId, employeeId);

        assertTrue(result, "Employee should be assigned to the project successfully");
        verify(repo, times(1)).assignProjectToEmployee(projectId, employeeId);
    }

    @Test
    public void testAssignTaskToEmployee() throws EmployeeNotFoundException, ProjectNotFoundException {
        int taskId = 1;
        int projectId = 2;
        int employeeId = 3;

        Task task = new Task();
        task.setTaskId(taskId);
        task.setProjectId(projectId);
        task.setEmployeeId(employeeId);
        task.setTaskName("Develop Feature");
        task.setStatus("In Progress");

        when(repo.checkProjectExists(projectId)).thenReturn(true);
        when(repo.checkEmployeeExists(employeeId)).thenReturn(true);

        when(repo.assignTaskToEmployee(taskId, projectId, employeeId)).thenReturn(true);

        boolean result = repo.assignTaskToEmployee(taskId, projectId, employeeId);

        assertTrue(result, "Task should be assigned to the employee successfully");
        verify(repo, times(1)).assignTaskToEmployee(taskId, projectId, employeeId);
    }

    @Test
    public void testCreateTask() throws EmployeeNotFoundException, ProjectNotFoundException {
        Task task = new Task();
        task.setTaskName("Develop Feature X");
        task.setProjectId(1);
        task.setEmployeeId(1);
        task.setStatus("In Progress");

        when(repo.checkProjectExists(task.getProjectId())).thenReturn(true);  // Mock project exists
        when(repo.checkEmployeeExists(task.getEmployeeId())).thenReturn(true);  // Mock employee exists

        when(repo.createTask(task)).thenReturn(true);

        boolean result = repo.createTask(task);

        assertTrue(result, "Task should be created successfully");
        verify(repo, times(1)).createTask(task);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> mockEmployees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setId(1);
        emp1.setName("John Doe");
        emp1.setDesignation("Developer");
        mockEmployees.add(emp1);

        Employee emp2 = new Employee();
        emp2.setId(2);
        emp2.setName("Jane Doe");
        emp2.setDesignation("Tester");
        mockEmployees.add(emp2);

        when(repo.getAllEmployees()).thenReturn(mockEmployees);

        List<Employee> employees = repo.getAllEmployees();

        assertEquals(2, employees.size(), "There should be two employees in the list");
        assertEquals("John Doe", employees.get(0).getName(), "First employee name should be John Doe");
        assertEquals("Jane Doe", employees.get(1).getName(), "Second employee name should be Jane Doe");
    }

    @Test
    public void testGetAllProjects() {
        List<Project> mockProjects = new ArrayList<>();
        Project project1 = new Project();
        project1.setId(1);
        project1.setProjectName("Project Alpha");
        mockProjects.add(project1);

        Project project2 = new Project();
        project2.setId(2);
        project2.setProjectName("Project Beta");
        mockProjects.add(project2);

        when(repo.getAllProjects()).thenReturn(mockProjects);

        List<Project> projects = repo.getAllProjects();

        assertEquals(2, projects.size(), "There should be two projects in the list");
        assertEquals("Project Alpha", projects.get(0).getProjectName(), "First project name should be Project Alpha");
        assertEquals("Project Beta", projects.get(1).getProjectName(), "Second project name should be Project Beta");
    }

    @Test
    public void testDeleteEmployee() throws EmployeeNotFoundException {
        int employeeId = 1;

        when(repo.checkEmployeeExists(employeeId)).thenReturn(true);  // Mock employee exists
        when(repo.deleteEmployee(employeeId)).thenReturn(true);

        boolean result = repo.deleteEmployee(employeeId);

        assertTrue(result, "Employee should be deleted successfully");
        verify(repo, times(1)).deleteEmployee(employeeId);
    }

    @Test
    public void testDeleteProject() throws ProjectNotFoundException {
        int projectId = 1;

        when(repo.checkProjectExists(projectId)).thenReturn(true);  // Mock project exists
        when(repo.deleteProject(projectId)).thenReturn(true);

        boolean result = repo.deleteProject(projectId);

        assertTrue(result, "Project should be deleted successfully");
        verify(repo, times(1)).deleteProject(projectId);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setTaskId(1);
        task1.setTaskName("Task 1");
        task1.setProjectId(1);
        task1.setEmployeeId(1);
        task1.setStatus("In Progress");
        mockTasks.add(task1);

        Task task2 = new Task();
        task2.setTaskId(2);
        task2.setTaskName("Task 2");
        task2.setProjectId(1);
        task2.setEmployeeId(2);
        task2.setStatus("Completed");
        mockTasks.add(task2);

        when(repo.getAllTasks(1, 1)).thenReturn(mockTasks);

        List<Task> tasks = repo.getAllTasks(1, 1);

        assertEquals(2, tasks.size(), "There should be two tasks for this employee in the project");
    }

    @Test
    public void testGetTaskTable() {
        List<Task> mockTasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setTaskId(1);
        task1.setTaskName("Task 1");
        task1.setProjectId(1);
        task1.setEmployeeId(1);
        task1.setStatus("In Progress");
        mockTasks.add(task1);

        Task task2 = new Task();
        task2.setTaskId(2);
        task2.setTaskName("Task 2");
        task2.setProjectId(1);
        task2.setEmployeeId(2);
        task2.setStatus("Completed");
        mockTasks.add(task2);

        when(repo.getTaskTable()).thenReturn(mockTasks);

        List<Task> tasks = repo.getTaskTable();

        assertEquals(2, tasks.size(), "There should be two tasks in the task table");
    }
}
