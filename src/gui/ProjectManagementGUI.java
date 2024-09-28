/*package gui;

import dao.IProjectRepository;
import dao.ProjectRepositoryImpl;
import entity.Employee;
import entity.Project;
import entity.Task;
import exception.EmployeeNotFoundException;
import exception.ProjectNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ProjectManagementGUI extends JFrame {
    private IProjectRepository repository;

    public ProjectManagementGUI() {
        repository = new ProjectRepositoryImpl();

        // Frame settings
        setTitle("Project Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 150, 200));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        JLabel titleLabel = new JLabel("Project Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {
                "Add Employee", "Add Project",
                "Add Task", "Assign Project",
                "Assign Task", "Delete Employee",
                "Delete Project", "List Tasks"
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        for (int i = 0; i < buttonLabels.length; i++) {
            RoundedButton button = createButton(buttonLabels[i]);
            gbc.gridx = i % 2; // Column
            gbc.gridy = i / 2; // Row
            buttonPanel.add(button, gbc);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setPreferredSize(new Dimension(800, 50));
        JLabel footerLabel = new JLabel("© 2024 Project Management Inc.");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private RoundedButton createButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(new ButtonClickListener(text)); // Add listener here
        return button;
    }

    private class ButtonClickListener implements ActionListener {
        private String buttonText;

        public ButtonClickListener(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonText) {
                case "Add Employee":
                    addEmployee();
                    break;
                case "Add Project":
                    addProject();
                    break;
                case "Add Task":
                    addTask();
                    break;
                case "Assign Project":
                    assignProject();
                    break;
                case "Assign Task":
                    assignTask();
                    break;
                case "Delete Employee":
                    deleteEmployee();
                    break;
                case "Delete Project":
                    deleteProject();
                    break;
                case "List Tasks":
                    listTasks();
                    break;
                default:
                    JOptionPane.showMessageDialog(ProjectManagementGUI.this, "Action not implemented yet");
            }
        }
    }

    private void addEmployee() {
        String name = JOptionPane.showInputDialog("Enter employee name:");
        String designation = JOptionPane.showInputDialog("Enter designation:");
        String gender = JOptionPane.showInputDialog("Enter gender:");
        double salary = Double.parseDouble(JOptionPane.showInputDialog("Enter salary:"));
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));

        Employee employee = new Employee(0, name, designation, gender, salary, projectId);
        boolean success = repository.createEmployee(employee);
        JOptionPane.showMessageDialog(this, success ? "✅ Employee added successfully." : "❌ Failed to add employee.");
    }

    private void addProject() {
        String projectName = JOptionPane.showInputDialog("Enter project name:");
        String description = JOptionPane.showInputDialog("Enter description:");
        LocalDate startDate = LocalDate.parse(JOptionPane.showInputDialog("Enter start date (YYYY-MM-DD):"));
        String status = JOptionPane.showInputDialog("Enter status:");

        Project project = new Project(0, projectName, description, startDate, status);
        boolean success = repository.createProject(project);
        JOptionPane.showMessageDialog(this, success ? "✅ Project added successfully." : "❌ Failed to add project.");
    }

    private void addTask() {
        String taskName = JOptionPane.showInputDialog("Enter task name:");
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));
        int employeeId = Integer.parseInt(JOptionPane.showInputDialog("Enter employee ID:"));
        String status = JOptionPane.showInputDialog("Enter status:");

        Task task = new Task(0, taskName, projectId, employeeId, status);
        try {
            boolean success = repository.createTask(task);
            JOptionPane.showMessageDialog(this, success ? "✅ Task added successfully." : "❌ Failed to add task.");
        } catch (EmployeeNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        } catch (ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void assignProject() {
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));
        int employeeId = Integer.parseInt(JOptionPane.showInputDialog("Enter employee ID:"));

        try {
            boolean success = repository.assignProjectToEmployee(projectId, employeeId);
            JOptionPane.showMessageDialog(this, success ? "✅ Project assigned successfully." : "❌ Failed to assign project.");
        } catch (EmployeeNotFoundException | ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void assignTask() {
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter task ID:"));
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));
        int employeeId = Integer.parseInt(JOptionPane.showInputDialog("Enter employee ID:"));

        try {
            boolean success = repository.assignTaskToEmployee(taskId, projectId, employeeId);
            JOptionPane.showMessageDialog(this, success ? "✅ Task assigned successfully." : "❌ Failed to assign task.");
        } catch (EmployeeNotFoundException | ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        int userId = Integer.parseInt(JOptionPane.showInputDialog("Enter employee ID:"));
        try {
            boolean success = repository.deleteEmployee(userId);
            JOptionPane.showMessageDialog(this, success ? "✅ Employee deleted successfully." : "❌ Failed to delete employee.");
        } catch (EmployeeNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void deleteProject() {
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));
        try {
            boolean success = repository.deleteProject(projectId);
            JOptionPane.showMessageDialog(this, success ? "✅ Project deleted successfully." : "❌ Failed to delete project.");
        } catch (ProjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage());
        }
    }

    private void listTasks() {
        int empId = Integer.parseInt(JOptionPane.showInputDialog("Enter employee ID:"));
        int projectId = Integer.parseInt(JOptionPane.showInputDialog("Enter project ID:"));
        List<Task> tasks = repository.getAllTasks(empId, projectId);

        StringBuilder taskList = new StringBuilder("Tasks in Project:\n");
        for (Task task : tasks) {
            taskList.append("Task ID: ").append(task.getTaskId())
                    .append(", Name: ").append(task.getTaskName())
                    .append(", Status: ").append(task.getStatus()).append("\n");
        }

        if (tasks.isEmpty()) {
            taskList.append("No tasks found for this project and employee.");
        }

        JOptionPane.showMessageDialog(this, taskList.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProjectManagementGUI::new);
    }
}

// Custom button class with rounded corners
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);
        setPreferredSize(new Dimension(150, 40)); // Set preferred size for consistency
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 40); // Set a fixed size for buttons
    }
}
*/