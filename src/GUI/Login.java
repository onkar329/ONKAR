package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Login {

    private final JPanel MiniPanel, MainPanel;
    private final JButton Close_Button, Login_Button;
    private final JLabel PW_Label, UN_Label, Image_jLabel, info_Label;
    private final JTextField UN_TextField;
    private final JPasswordField Password_Field;
    private final JButton togglePasswordButton;
    private boolean isPasswordVisible = false;

    public Login() {

        // Main Panel (Background Image)
        MainPanel = new JPanel();
        MainPanel.setMinimumSize(new Dimension(1366, 768));
        MainPanel.setLayout(new AbsoluteLayout());

        // Mini Panel (Improved layout with rounded corners)
        MiniPanel = new JPanel();
        MiniPanel.setBackground(new Color(0, 0, 0, 100));
        MiniPanel.setForeground(Color.WHITE);
        MiniPanel.setLayout(new GridBagLayout());
        MiniPanel.setPreferredSize(new Dimension(450, 500));
        MiniPanel.setBorder(new LineBorder(Color.WHITE, 3, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        info_Label = new JLabel("Please Enter Your Login Details");
        info_Label.setFont(new Font("Arial", Font.BOLD, 28));
        info_Label.setForeground(Color.WHITE);
        info_Label.setHorizontalAlignment(SwingConstants.CENTER);

        UN_Label = new JLabel("Username:");
        UN_Label.setFont(new Font("Arial", Font.PLAIN, 20));
        UN_Label.setForeground(Color.WHITE);

        PW_Label = new JLabel("Password:");
        PW_Label.setFont(new Font("Arial", Font.PLAIN, 20));
        PW_Label.setForeground(Color.WHITE);

        // Text Fields
        UN_TextField = new JTextField(20);
        Password_Field = new JPasswordField(20);
        UN_TextField.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        Password_Field.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        UN_TextField.setBackground(new Color(240, 240, 240));
        Password_Field.setBackground(new Color(240, 240, 240));

        // Eye Button for toggling password visibility
        togglePasswordButton = new JButton("Show");
        togglePasswordButton.setPreferredSize(new Dimension(80, 25));
        togglePasswordButton.setFocusable(false);
        togglePasswordButton.addActionListener(e -> togglePasswordVisibility());

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(0, 0, 0, 0));
        passwordPanel.add(Password_Field, BorderLayout.CENTER);
        passwordPanel.add(togglePasswordButton, BorderLayout.EAST);

        // Buttons
        Login_Button = new JButton("Login");
        Close_Button = new JButton("Close");
        Login_Button.setPreferredSize(new Dimension(140, 45));
        Close_Button.setPreferredSize(new Dimension(140, 45));
        Login_Button.setBackground(new Color(0, 123, 255));
        Login_Button.setForeground(Color.WHITE);
        Close_Button.setBackground(new Color(220, 53, 69));
        Close_Button.setForeground(Color.WHITE);

        // Adding Components to MiniPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        MiniPanel.add(info_Label, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        MiniPanel.add(UN_Label, gbc);
        gbc.gridx = 1;
        MiniPanel.add(UN_TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        MiniPanel.add(PW_Label, gbc);
        gbc.gridx = 1;
        MiniPanel.add(passwordPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        MiniPanel.add(Login_Button, gbc);
        gbc.gridx = 1;
        MiniPanel.add(Close_Button, gbc);

        // Background Image
        Image_jLabel = new JLabel();
        Image_jLabel.setMinimumSize(new Dimension(1366, 768));
        Image_jLabel.setIcon(new ImageIcon("LoginImage.jpg"));

        // Adding Components to MainPanel
        MainPanel.add(MiniPanel, new AbsoluteConstraints(0, 0, 450, 550));
        MainPanel.add(Image_jLabel, new AbsoluteConstraints(0, 0));

        // Bottom Black Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(1366, 500));
        MainPanel.add(bottomPanel, new AbsoluteConstraints(0, 400, 1366, 300));

        // Button Actions
        Login_Button.addActionListener(new LoginActionListener());
        Close_Button.addActionListener(new LoginActionListener());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            Password_Field.setEchoChar('•'); // hide password
            togglePasswordButton.setText("Show");
        } else {
            Password_Field.setEchoChar((char) 0); // show password
            togglePasswordButton.setText("Hide");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Close":
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Close Confirmation", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        System.exit(0);
                    }
                    break;
                case "Login":
                    if (UN_TextField.getText().equalsIgnoreCase("ONKAR") && new String(Password_Field.getPassword()).equals("2158")) {
                        JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        Runner.getFrame().dispose();
                        Parent_JFrame frame = new Parent_JFrame();
                        MainMenu menu = new MainMenu();
                        JFrame mainFrame = Parent_JFrame.getMainFrame();
                        mainFrame.getContentPane().removeAll();
                        mainFrame.add(menu.getMainPanel());
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        mainFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username/Password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        }
    }
}
