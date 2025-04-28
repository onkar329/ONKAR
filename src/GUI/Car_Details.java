package GUI;

import BackendCode.Booking;
import BackendCode.Car;
import BackendCode.CarOwner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Car_Details {

    private static DefaultTableModel tablemodel; // it is made static so that it can be accessed in add GUI class to update the Jtable when a new record is added

    private static JButton SearchName_Button, SearchRegNo_Button, Add_Button,
            Update_Button, Remove_Button, BackButton, LogoutButton;
    private static JTextField SearchName_TextField, SearchRegNo_TextField;
    private static JScrollPane jScrollPane1;
    private static JTable jTable1;
    private JPanel MainPanel;

    public Car_Details() {
        // Initialize the main panel with a light gray background
        MainPanel = new JPanel();
        MainPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        Parent_JFrame.getMainFrame().setTitle("Car Details - Rent-A-Car Management System");
        MainPanel.setLayout(new AbsoluteLayout());
        MainPanel.setMinimumSize(new Dimension(1366, 730));

        // Initialize buttons and text fields
        SearchRegNo_Button = new JButton("Search Reg_No");
        SearchRegNo_TextField = new JTextField();
        SearchName_Button = new JButton("Search Name");
        SearchName_TextField = new JTextField();
        Add_Button = new JButton("Add");
        Update_Button = new JButton("Update");
        Remove_Button = new JButton("Remove");
        BackButton = new JButton("Back");
        LogoutButton = new JButton("Logout");

        // Style buttons with modern colors
        styleButton(SearchRegNo_Button, new Color(52, 152, 219)); // Blue
        styleButton(SearchName_Button, new Color(46, 204, 113)); // Green
        styleButton(Add_Button, new Color(155, 89, 182)); // Purple
        styleButton(Update_Button, new Color(241, 196, 15)); // Yellow
        styleButton(Remove_Button, new Color(231, 76, 60)); // Red
        styleButton(BackButton, new Color(26, 188, 156)); // Turquoise
        styleButton(LogoutButton, new Color(52, 73, 94)); // Dark Blue

        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        // Initialize the table model with column names
        String[] columns = {"Sr#", "ID", "Maker", "Name", "Colour", "Type", "Seats", "Model", "Condition",
            "Reg No.", "Rent/hour", "Car Owner"};
        tablemodel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        jTable1 = new JTable(getTablemodel());
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Modern Table Design
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Modern font
        jTable1.setRowHeight(30); // Increase row height for better spacing
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new Color(200, 200, 200)); // Light gray grid color

        // Alternating row colors for light theme
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 255, 255) : new Color(245, 245, 245)); // White and light gray
                    c.setForeground(Color.BLACK); // Black text
                } else {
                    c.setBackground(new Color(52, 152, 219)); // Blue for selected row
                    c.setForeground(Color.WHITE); // White text
                }
                return c;
            }
        });

        // Customize table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Bold header font
        header.setBackground(new Color(52, 152, 219)); // Blue header background
        header.setForeground(Color.WHITE); // White header text
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Increase header height

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adjust column widths
        jTable1.getColumnModel().getColumn(0).setMinWidth(20);
        jTable1.getColumnModel().getColumn(1).setMinWidth(20);
        jTable1.getColumnModel().getColumn(2).setMinWidth(170);
        jTable1.getColumnModel().getColumn(3).setMinWidth(170);
        jTable1.getColumnModel().getColumn(4).setMinWidth(140);
        jTable1.getColumnModel().getColumn(5).setMinWidth(150);
        jTable1.getColumnModel().getColumn(6).setMinWidth(90);
        jTable1.getColumnModel().getColumn(7).setMinWidth(90);
        jTable1.getColumnModel().getColumn(8).setMinWidth(160);
        jTable1.getColumnModel().getColumn(9).setMinWidth(170);
        jTable1.getColumnModel().getColumn(10).setMinWidth(150);
        jTable1.getColumnModel().getColumn(11).setMinWidth(150);

        jTable1.getTableHeader().setReorderingAllowed(false);

        jScrollPane1.setViewportView(jTable1);

        // Populate table with car data
        populateTable();

        // Add components to the main panel
        MainPanel.add(SearchRegNo_Button, new AbsoluteConstraints(10, 15, 130, 22));
        MainPanel.add(SearchRegNo_TextField, new AbsoluteConstraints(145, 15, 240, 22));
        MainPanel.add(SearchName_Button, new AbsoluteConstraints(390, 15, 130, 22));
        MainPanel.add(SearchName_TextField, new AbsoluteConstraints(525, 15, 240, 22));
        MainPanel.add(jScrollPane1, new AbsoluteConstraints(10, 50, 1330, 550));
        MainPanel.add(Remove_Button, new AbsoluteConstraints(785, 625, 130, 22));
        MainPanel.add(Add_Button, new AbsoluteConstraints(450, 625, 130, 22));
        MainPanel.add(Update_Button, new AbsoluteConstraints(620, 625, 130, 22));
        MainPanel.add(BackButton, new AbsoluteConstraints(1106, 625, 100, 22));
        MainPanel.add(LogoutButton, new AbsoluteConstraints(1236, 625, 100, 22));

        // Add action listeners
        addActionListeners();
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    private void populateTable() {
        ArrayList<Car> Car_objects = Car.View();
        for (int i = 0; i < Car_objects.size(); i++) {
            int ID = Car_objects.get(i).getID();
            String maker = Car_objects.get(i).getMaker();
            String Name = Car_objects.get(i).getName();
            String color = Car_objects.get(i).getColour();
            String type = Car_objects.get(i).getType();
            int seatingCapacity = Car_objects.get(i).getSeatingCapacity();
            String model = Car_objects.get(i).getModel();
            String condition = Car_objects.get(i).getCondition();
            String regNo = Car_objects.get(i).getRegNo();
            int rentPerHour = Car_objects.get(i).getRentPerHour();
            CarOwner carOwner = Car_objects.get(i).getCarOwner();

            String[] one_s_Record = {((i + 1) + ""), "" + ID, maker, Name, color, type, seatingCapacity + "",
                model, condition, regNo, rentPerHour + "", carOwner.getID() + ": " + carOwner.getName()};
            tablemodel.addRow(one_s_Record);
        }
    }

    private void addActionListeners() {
        SearchName_Button.addActionListener(new Car_Details_ActionListener());
        SearchRegNo_Button.addActionListener(new Car_Details_ActionListener());
        Add_Button.addActionListener(new Car_Details_ActionListener());
        Update_Button.addActionListener(new Car_Details_ActionListener());
        Remove_Button.addActionListener(new Car_Details_ActionListener());
        BackButton.addActionListener(new Car_Details_ActionListener());
        LogoutButton.addActionListener(new Car_Details_ActionListener());
    }

    public static DefaultTableModel getTablemodel() {
        return tablemodel;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private class Car_Details_ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Search Reg_No": {
                    String regNo = SearchRegNo_TextField.getText().trim();
                    if (!regNo.isEmpty()) {
                        if (Car.isRegNoValid(regNo)) {
                            Car car = Car.SearchByRegNo(regNo);
                            if (car != null) {
                                JOptionPane.showMessageDialog(null, car.toString());
                                SearchRegNo_TextField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "Required Car not found");
                                SearchRegNo_TextField.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Reg No.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Enter Car Reg no first !");
                    }
                }
                break;
                case "Search Name": {
                    String name = SearchName_TextField.getText().trim();
                    if (!name.isEmpty()) {
                        if (Car.isNameValid(name)) {
                            ArrayList<Car> car = Car.SearchByName(name);
                            if (!car.isEmpty()) {
                                JOptionPane.showMessageDialog(null, car.toString());
                                SearchName_TextField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "Required Car not found");
                                SearchName_TextField.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Name !");
                            SearchName_TextField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Enter Car Name first !");
                    }
                }
                break;
                case "Add": {
                    Parent_JFrame.getMainFrame().setEnabled(false);
                    Car_Add ac = new Car_Add();
                    ac.setVisible(true);
                }
                break;
                case "Update": {
                    Parent_JFrame.getMainFrame().setEnabled(false);
                    Car_Update ac = new Car_Update();
                    ac.setVisible(true);
                }
                break;
                case "Remove": {
                    Parent_JFrame.getMainFrame().setEnabled(false);
                    Car_Remove ac = new Car_Remove();
                    ac.setVisible(true);
                }
                break;
                case "Back": {
                    Parent_JFrame.getMainFrame().setTitle("Rent-A-Car Management System [REBORN]");
                    MainMenu mm = new MainMenu();
                    Parent_JFrame.getMainFrame().getContentPane().removeAll();
                    Parent_JFrame.getMainFrame().add(mm.getMainPanel());
                    Parent_JFrame.getMainFrame().getContentPane().revalidate();
                }
                break;
                case "Logout": {
                    Parent_JFrame.getMainFrame().dispose();
                    Runner r = new Runner();
                    JFrame frame = r.getFrame();
                    Login login = new Login();
                    JPanel panel = login.getMainPanel();
                    frame.add(panel);
                    frame.setVisible(true);
                }
                break;
            }
        }
    }
}