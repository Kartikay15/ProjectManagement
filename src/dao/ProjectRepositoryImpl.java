package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {

    @Override
    public boolean createEmployee(Employee emp) throws ProjectNotFoundException {
        if (checkProjectExists(emp.getProjectId())) {
            throw new ProjectNotFoundException("Project with ID " + emp.getProjectId() + " not found.");
        }
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Employee (name, designation, gender, salary, project_id) VALUES (?, ?, ?, ?, ?)";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getDesignation());
            pstmt.setString(3, emp.getGender());
            pstmt.setDouble(4, emp.getSalary());
            pstmt.setInt(5, emp.getProjectId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean createProject(Project pj) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Project (projectName, description, startDate, status) VALUES (?, ?, ?, ?)";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, pj.getProjectName());
            pstmt.setString(2, pj.getDescription());
            pstmt.setDate(3, Date.valueOf(pj.getStartDate()));
            pstmt.setString(4, pj.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createTask(Task task) throws EmployeeNotFoundException, ProjectNotFoundException {
        if (checkEmployeeExists(task.getEmployeeId())) {
            throw new EmployeeNotFoundException("Employee with ID " + task.getEmployeeId() + " not found.");
        }
        if (checkProjectExists(task.getProjectId())) {
            throw new ProjectNotFoundException("Project with ID " + task.getProjectId() + " not found.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Task (task_name, project_id, employee_id, status) VALUES (?, ?, ?, ?)";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, task.getTaskName());
            pstmt.setInt(2, task.getProjectId());
            pstmt.setInt(3, task.getEmployeeId());
            pstmt.setString(4, task.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignProjectToEmployee(int projectId, int employeeId) throws EmployeeNotFoundException, ProjectNotFoundException {
        if (checkProjectExists(projectId)) {
            throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
        }
        if (checkEmployeeExists(employeeId)) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "UPDATE Employee SET project_id = ? WHERE id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, employeeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean assignTaskToEmployee(int taskId, int projectId, int employeeId) throws EmployeeNotFoundException, ProjectNotFoundException {
        if (checkProjectExists(projectId)) {
            throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
        }
        if (checkEmployeeExists(employeeId)) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "UPDATE Task SET employee_id = ? WHERE task_id = ? AND project_id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, taskId);
            pstmt.setInt(3, projectId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(int userId) throws EmployeeNotFoundException {
        if (checkEmployeeExists(userId)) {
            throw new EmployeeNotFoundException("Employee with ID " + userId + " not found.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "DELETE FROM Employee WHERE id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProject(int projectId) throws ProjectNotFoundException {
        if (checkProjectExists(projectId)) {
            throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "DELETE FROM Project WHERE id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, projectId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Task> getAllTasks(int empId, int projectId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Task WHERE employee_id = ? AND project_id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, empId);
            pstmt.setInt(2, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setProjectId(rs.getInt("project_id"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setStatus(rs.getString("status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Employee";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setDesignation(rs.getString("designation"));
                employee.setGender(rs.getString("gender"));
                employee.setSalary(rs.getInt("salary"));
                employee.setProjectId(rs.getInt("project_id"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Project";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setProjectName(rs.getString("projectName"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(LocalDate.parse(rs.getString("startDate")));
                project.setStatus(rs.getString("status"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public List<Task> getTaskTable() {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Task";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskName(rs.getString("task_name"));
                task.setProjectId(rs.getInt("project_id"));
                task.setEmployeeId(rs.getInt("employee_id"));
                task.setStatus(rs.getString("status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private boolean checkEmployeeExists(int employeeId) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT COUNT(*) FROM Employee WHERE id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) <= 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean checkProjectExists(int projectId) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT COUNT(*) FROM Project WHERE id = ?";
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) <= 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
