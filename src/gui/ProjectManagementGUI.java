package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManagementGUI extends JFrame {

    public ProjectManagementGUI() {
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

        // Button Panel with GridBagLayout for better control
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {
                "Add Employee", "Add Project",
                "Add Task", "Assign Project",
                "Assign Task", "Delete Employee",
                "Delete Task", "List Projects"
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
            JOptionPane.showMessageDialog(ProjectManagementGUI.this, buttonText + " button clicked");
        }
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
