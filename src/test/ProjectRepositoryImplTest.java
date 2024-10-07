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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProjectRepositoryImplTest {

    // Create a mock instance of ProjectRepositoryImpl
    private ProjectRepositoryImpl repo;

    @BeforeEach
    public void setUp() {
        // Initialize the mock object before each test
        repo = mock(ProjectRepositoryImpl.class);
    }

    @Test
    public void testCreateEmployee() throws ProjectNotFoundException {
        // Arrange: create an Employee object for the test
        Employee emp = new Employee();
        emp.setName("John");
        emp.setDesignation("Developer");
        emp.setGender("Male");
        emp.setSalary(5000);
        emp.setProjectId(1);  // Ensure a valid project ID

        // Mock the behavior of the repository method
        when(repo.createEmployee(emp)).thenReturn(true);

        // Act: Call the method under test
        boolean result = repo.createEmployee(emp);

        // Assert: Check if the method returns true (successful employee creation)
        assertTrue(result, "Employee should be created successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).createEmployee(emp);
    }

    @Test
    public void testCreateProject() {
        // Arrange: Create a Project object for the test
        Project project = new Project();
        project.setProjectName("Project Alpha");
        project.setDescription("New software project");
        project.setStartDate(java.time.LocalDate.now());
        project.setStatus("Active");

        // Mock the behavior of the repository method
        when(repo.createProject(project)).thenReturn(true);

        // Act: Call the method under test
        boolean result = repo.createProject(project);

        // Assert: Check if the method returns true (successful project creation)
        assertTrue(result, "Project should be created successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).createProject(project);
    }

    @Test
    public void testAssignProjectToEmployee() throws EmployeeNotFoundException, ProjectNotFoundException {
        // Arrange: Employee ID and Project ID for assignment
        int employeeId = 1;
        int projectId = 2;

        // Mock the check if the project and employee exist
        when(repo.checkProjectExists(projectId)).thenReturn(false);  // Mock project exists
        when(repo.checkEmployeeExists(employeeId)).thenReturn(false);  // Mock employee exists

        // Mock the assignment to return true
        when(repo.assignProjectToEmployee(projectId, employeeId)).thenReturn(true);

        // Act: Call the method to assign project to employee
        boolean result = repo.assignProjectToEmployee(projectId, employeeId);

        // Assert: Check if the assignment was successful
        assertTrue(result, "Employee should be assigned to the project successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).assignProjectToEmployee(projectId, employeeId);
    }

    @Test
    public void testCreateTask() throws EmployeeNotFoundException, ProjectNotFoundException {
        // Arrange: Create a Task object for the test
        Task task = new Task();
        task.setTaskName("Develop Feature X");
        task.setProjectId(1);
        task.setEmployeeId(1);
        task.setStatus("In Progress");

        // Mock the behavior of the repository method
        when(repo.checkProjectExists(task.getProjectId())).thenReturn(false);  // Mock project exists
        when(repo.checkEmployeeExists(task.getEmployeeId())).thenReturn(false);  // Mock employee exists

        // Mock the method to return true for successful task creation
        when(repo.createTask(task)).thenReturn(true);

        // Act: Call the method under test
        boolean result = repo.createTask(task);

        // Assert: Verify if the task was created successfully
        assertTrue(result, "Task should be created successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).createTask(task);
    }

    @Test
    public void testGetAllEmployees() {
        // Arrange: Mock the behavior of the method to return a list of employees
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

        // Act: Call the method to get all employees
        List<Employee> employees = repo.getAllEmployees();

        // Assert: Verify the employees are returned as expected
        assertEquals(2, employees.size(), "There should be two employees in the list");
        assertEquals("John Doe", employees.get(0).getName(), "First employee name should be John Doe");
        assertEquals("Jane Doe", employees.get(1).getName(), "Second employee name should be Jane Doe");
    }

    @Test
    public void testDeleteEmployee() throws EmployeeNotFoundException {
        // Arrange: Employee ID to delete
        int employeeId = 1;

        // Mock the behavior of the repository method
        when(repo.checkEmployeeExists(employeeId)).thenReturn(false);  // Mock employee exists
        when(repo.deleteEmployee(employeeId)).thenReturn(true);

        // Act: Call the method to delete employee
        boolean result = repo.deleteEmployee(employeeId);

        // Assert: Verify the employee was deleted successfully
        assertTrue(result, "Employee should be deleted successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).deleteEmployee(employeeId);
    }

    @Test
    public void testDeleteProject() throws ProjectNotFoundException {
        // Arrange: Project ID to delete
        int projectId = 1;

        // Mock the behavior of the repository method
        when(repo.checkProjectExists(projectId)).thenReturn(false);  // Mock project exists
        when(repo.deleteProject(projectId)).thenReturn(true);

        // Act: Call the method to delete project
        boolean result = repo.deleteProject(projectId);

        // Assert: Verify the project was deleted successfully
        assertTrue(result, "Project should be deleted successfully");

        // Verify that the method was called on the mock
        verify(repo, times(1)).deleteProject(projectId);
    }

    @Test
    public void testGetAllProjects() {
        // Arrange: Mock the behavior of the method to return a list of projects
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

        // Act: Call the method to get all projects
        List<Project> projects = repo.getAllProjects();

        // Assert: Verify the projects are returned as expected
        assertEquals(2, projects.size(), "There should be two projects in the list");
        assertEquals("Project Alpha", projects.get(0).getProjectName(), "First project name should be Project Alpha");
        assertEquals("Project Beta", projects.get(1).getProjectName(), "Second project name should be Project Beta");
    }
}
