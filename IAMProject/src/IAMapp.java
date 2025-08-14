import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class IAMapp extends JFrame {

    private JTextField usernameField, roleField;
    private JTextArea userDisplayArea;
    private Map<String, String> users;

    public IAMapp() {
        users = new HashMap<>();

        setTitle("🔥 Identity & Access Management Dashboard 🔐");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(34, 40, 49));

      
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(34, 40, 49));
        JLabel iconLabel = new JLabel(new ImageIcon(createColoredCircle(40, new Color(100, 149, 237))));
        JLabel titleLabel = new JLabel("IAM Access Control");
        titleLabel.setForeground(new Color(178, 190, 195));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.add(iconLabel);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(titleLabel);
        header.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(header, BorderLayout.NORTH);

    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(45, 52, 54));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(178, 190, 195));
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(usernameField, gbc);

        JLabel roleLabel = new JLabel("Role (Admin/User/Viewer):");
        roleLabel.setForeground(new Color(178, 190, 195));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(roleLabel, gbc);

        roleField = new JTextField(15);
        roleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(roleField, gbc);


        JButton addButton = new JButton("➕ Add User");
        styleButton(addButton, new Color(46, 204, 113));
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(addButton, gbc);

        JButton checkAccessButton = new JButton("🔍 Check Access");
        styleButton(checkAccessButton, new Color(52, 152, 219));
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(checkAccessButton, gbc);

        add(inputPanel, BorderLayout.CENTER);


        userDisplayArea = new JTextArea();
        userDisplayArea.setEditable(false);
        userDisplayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        userDisplayArea.setBackground(new Color(22, 28, 36));
        userDisplayArea.setForeground(new Color(46, 204, 113));
        userDisplayArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(new JScrollPane(userDisplayArea), BorderLayout.SOUTH);
        ((JScrollPane)getContentPane().getComponent(2)).setPreferredSize(new Dimension(700, 180));


        addButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String role = roleField.getText().trim().toLowerCase();
            if (!username.isEmpty() && !role.isEmpty()) {
                users.put(username, role);
                refreshUserList();
                usernameField.setText("");
                roleField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please enter both username and role.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        checkAccessButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a username to check access.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String message = checkAccess(username);
            JOptionPane.showMessageDialog(this,
                    message,
                    "Access Result",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void refreshUserList() {
        StringBuilder sb = new StringBuilder();
        if (users.isEmpty()) {
            sb.append("No users found.");
        } else {
            users.forEach((username, role) -> sb.append("User: ").append(username).append(" | Role: ").append(role).append("\n"));
        }
        userDisplayArea.setText(sb.toString());
    }

    private String checkAccess(String username) {
        String role = users.get(username);
        if (role == null) {
            return "Access Denied: User not found.";
        }
        switch (role) {
            case "admin":
                return "Access Granted: Admin access.";
            case "user":
                return "Access Granted: Limited user access.";
            case "viewer":
                return "Access Granted: Read-only access.";
            default:
                return "Access Denied: Unknown role.";
        }
    }

    private Image createColoredCircle(int diameter, Color color) {
 
        Image img = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillOval(0, 0, diameter, diameter);
        g2.dispose();
        return img;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IAMapp::new);
    }
} 
