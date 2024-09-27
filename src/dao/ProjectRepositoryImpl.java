package dao;

import entity.Employee;
import entity.Project;
import entity.Task;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {
    @Override
    public boolean createEmployee(Employee emp) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Employee (name, designation, gender, salary, project_id) VALUES (?, ?, ?, ?, ?)";
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
    public boolean createTask(Task task) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Task (task_name, project_id, employee_id, status) VALUES (?, ?, ?, ?)";
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
    public boolean assignProjectToEmployee(int projectId, int employeeId) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "UPDATE Employee SET project_id = ? WHERE id = ?";
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
    public boolean assignTaskToEmployee(int taskId, int projectId, int employeeId) {
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
    public boolean deleteEmployee(int userId) {
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
    public boolean deleteProject(int projectId) {
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
}
