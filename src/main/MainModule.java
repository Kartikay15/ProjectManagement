package main;

import dao.IProjectRepository;
import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IProjectRepository repository = new ProjectRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("Enter 0 for console mode and 1 for GUI Mode");
        int choice = scanner.nextInt();
        if (choice==1) {
            // Launch GUI
            //javax.swing.SwingUtilities.invokeLater(ProjectManagementGUI::new);
        } else {
            // Launch Console
            runConsole();
        }
    }

    private static void runConsole() {
        Map<Integer, Runnable> actions = new HashMap<>();
        actions.put(1, MainModule::addEmployee);
        actions.put(2, MainModule::addProject);
        actions.put(3, MainModule::addTask);
        actions.put(4, MainModule::assignProjectToEmployee);
        actions.put(5, MainModule::assignTaskToEmployee);
        actions.put(6, MainModule::deleteEmployee);
        actions.put(7, MainModule::deleteProject);
        actions.put(8, MainModule::listAllTasks);

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Exiting the application. Goodbye!");
                return;
            }

            Runnable action = actions.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void displayMenu() {
        // ASCII colors
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String GREEN = "\u001B[32m";
        final String CYAN = "\u001B[36m";
        final String YELLOW = "\u001B[33m";
        final String RED = "\u001B[31m";
        final String WHITE = "\u001B[37m";

        System.out.println(BLUE + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║" + RESET + YELLOW + "        Project Management System       " + RESET + BLUE + "║" + RESET);
        System.out.println(BLUE + "╠════════════════════════════════════════╣" + RESET);

        System.out.println(BLUE + "║" + RESET + GREEN + " 1. " + RESET + WHITE + "Add Employee                        " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 2. " + RESET + WHITE + "Add Project                         " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 3. " + RESET + WHITE + "Add Task                            " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 4. " + RESET + WHITE + "Assign Project to Employee          " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 5. " + RESET + WHITE + "Assign Task to Employee             " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 6. " + RESET + WHITE + "Delete Employee                     " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 7. " + RESET + WHITE + "Delete Project                      " + BLUE + "║" + RESET);
        System.out.println(BLUE + "║" + RESET + GREEN + " 8. " + RESET + WHITE + "List All Tasks in a Project         " + BLUE + "║" + RESET);

        System.out.println(BLUE + "╠════════════════════════════════════════╣" + RESET);
        System.out.println(BLUE + "║" + RESET + RED + " 0. " + RESET + WHITE + "Exit                                " + BLUE + "║" + RESET);
        System.out.println(BLUE + "╚════════════════════════════════════════╝" + RESET);

        System.out.print(CYAN + "Please select an option: " + RESET);
    }

    private static void addEmployee() {
        System.out.println("----- Add Employee -----");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        scanner.nextLine();

        Employee employee = new Employee(0, name, designation, gender, salary, projectId);
        try {
            boolean success = repository.createEmployee(employee);
            System.out.println(success ? "✅ Employee added successfully." : "❌ Failed to add employee.");
        } catch (ProjectNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void addProject() {
        System.out.println("----- Add Project -----");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        Project project = new Project(0, projectName, description, startDate, status);
        boolean success = repository.createProject(project);
        System.out.println(success ? "✅ Project added successfully." : "❌ Failed to add project.");
    }

    private static void addTask() {
        System.out.println("----- Add Task -----");
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.print("Enter status: ");
        scanner.nextLine(); // Consume newline
        String status = scanner.nextLine();

        Task task = new Task(0, taskName, projectId, employeeId, status);
        try {
            boolean success = repository.createTask(task);
            System.out.println(success ? "✅ Task added successfully." : "❌ Failed to add task.");
        } catch (EmployeeNotFoundException | ProjectNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void assignProjectToEmployee() {
        System.out.println("----- Assign Project to Employee -----");
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter employee ID: ");
        int employeeId = scanner.nextInt();
        try {
            boolean success = repository.assignProjectToEmployee(projectId, employeeId);
            System.out.println(success ? "✅ Project assigned successfully." : "❌ Failed to assign project.");
        } catch (EmployeeNotFoundException | ProjectNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void assignTaskToEmployee() {
        System.out.println("----- Assign Task to Employee -----");
        System.out.print("Enter task ID: ");
        int taskId = scanner.nextInt();
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        System.out.print("Enter employee ID: ");
        int employeeId = scanner.nextInt();
        try {
            boolean success = repository.assignTaskToEmployee(taskId, projectId, employeeId);
            System.out.println(success ? "✅ Task assigned successfully." : "❌ Failed to assign task.");
        } catch (EmployeeNotFoundException | ProjectNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        System.out.println("----- Delete Employee -----");
        System.out.print("Enter employee ID: ");
        int userId = scanner.nextInt();
        try {
            boolean success = repository.deleteEmployee(userId);
            System.out.println(success ? "✅ Employee deleted successfully." : "❌ Failed to delete employee.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void deleteProject() {
        System.out.println("----- Delete Project -----");
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        try {
            boolean success = repository.deleteProject(projectId);
            System.out.println(success ? "✅ Project deleted successfully." : "❌ Failed to delete project.");
        } catch (ProjectNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void listAllTasks() {
        System.out.println("----- List All Tasks -----");
        System.out.print("Enter employee ID: ");
        int empId = scanner.nextInt();
        System.out.print("Enter project ID: ");
        int projectId = scanner.nextInt();
        List<Task> tasks = repository.getAllTasks(empId, projectId);
        System.out.println("\nTasks in Project:");
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskId() + ", Name: " + task.getTaskName() + ", Status: " + task.getStatus());
        }
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for this project and employee.");
        }
    }
}
