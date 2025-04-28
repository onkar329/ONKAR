package GUI;

import BackendCode.Booking;
import BackendCode.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Customer_Details implements ActionListener {

    private JTextField SearchID_TextField;
    private JButton SearchID_Button, SearchName_Button, Update_Button, Add_Button, Remove_Button, Back_Button, Logout_Button, ClearBill_Button, ViewBill_Button;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField SearchName_TextField;
    static DefaultTableModel tablemodel;
    private JPanel MainPanel;

    public Customer_Details() {
        // Initialize the main panel with a light gray background
        MainPanel = new JPanel();
        MainPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        Parent_JFrame.getMainFrame().setTitle("Customer Details - Rent-A-Car Management System");
        MainPanel.setLayout(new AbsoluteLayout());
        MainPanel.setMinimumSize(new Dimension(1366, 730));

        // Initialize buttons and text fields
        SearchID_Button = new JButton("Search ID");
        Update_Button = new JButton("Update");
        Add_Button = new JButton("Add");
        Remove_Button = new JButton("Remove");
        Back_Button = new JButton("Back");
        Logout_Button = new JButton("Logout");
        SearchName_Button = new JButton("Search Name");
        ClearBill_Button = new JButton("Clear Bill");
        ViewBill_Button = new JButton("View Bill");
        SearchID_TextField = new JTextField();
        SearchName_TextField = new JTextField();
        jScrollPane1 = new JScrollPane();

        // Style buttons with modern colors
        styleButton(SearchID_Button, new Color(52, 152, 219)); // Blue
        styleButton(SearchName_Button, new Color(46, 204, 113)); // Green
        styleButton(Update_Button, new Color(241, 196, 15)); // Yellow
        styleButton(Add_Button, new Color(155, 89, 182)); // Purple
        styleButton(Remove_Button, new Color(231, 76, 60)); // Red
        styleButton(Back_Button, new Color(26, 188, 156)); // Turquoise
        styleButton(Logout_Button, new Color(52, 73, 94)); // Dark Blue
        styleButton(ClearBill_Button, new Color(230, 126, 34)); // Orange
        styleButton(ViewBill_Button, new Color(52, 152, 219)); // Yellow

        // Initialize the table model with column names
        String[] columns = {"Sr#", "ID", "CNIC", "Name", "Contact Number", "Car Rented", "Bill"};
        tablemodel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        // Initialize the JTable with the table model
        jTable1 = new JTable(tablemodel);

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
        JTableHeader header = jTable1.getTableHeader(); // Ensure jTable1 is initialized before accessing its header
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(70);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(170);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);

        jScrollPane1.setViewportView(jTable1);

        // Populate table with customer data
        populateTable();

        // Add components to the main panel
        MainPanel.add(SearchID_Button, new AbsoluteConstraints(390, 10, 130, 22));
        MainPanel.add(SearchID_TextField, new AbsoluteConstraints(525, 10, 240, 22));
        MainPanel.add(SearchName_Button, new AbsoluteConstraints(10, 10, 130, 22));
        MainPanel.add(SearchName_TextField, new AbsoluteConstraints(145, 10, 240, 22));
        MainPanel.add(jScrollPane1, new AbsoluteConstraints(10, 50, 1330, 550));
        MainPanel.add(Update_Button, new AbsoluteConstraints(579, 625, 130, 22));
        MainPanel.add(Add_Button, new AbsoluteConstraints(420, 625, 130, 22));
        MainPanel.add(Remove_Button, new AbsoluteConstraints(735, 625, 130, 22));
        MainPanel.add(Back_Button, new AbsoluteConstraints(1106, 625, 100, 22));
        MainPanel.add(Logout_Button, new AbsoluteConstraints(1236, 625, 100, 22));
        MainPanel.add(ClearBill_Button, new AbsoluteConstraints(10, 625, 200, 22));
        MainPanel.add(ViewBill_Button, new AbsoluteConstraints(220, 625, 130, 22));

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
        ArrayList<Customer> Customer_objects = Customer.View();
        for (int i = 0; i < Customer_objects.size(); i++) {
            int ID = Customer_objects.get(i).getID();
            String CNIC = Customer_objects.get(i).getCNIC();
            String Name = Customer_objects.get(i).getName();
            String ContactNo = Customer_objects.get(i).getContact_No();
            int Bill = Customer_objects.get(i).getBill();

            // Getting booked cars for customer
            ArrayList<Booking> bookings = Booking.SearchByCustomerID(ID);
            String bookedCars = "";
            if (!bookings.isEmpty()) {
                for (int j = 0; j < bookings.size(); j++) {
                    if (bookings.get(j).getReturnTime() == 0) {
                        bookedCars += bookings.get(j).getCar().getID() + ": " + bookings.get(j).getCar().getName() + "\n";
                    } else {
                        bookedCars = "No Cars Booked !";
                    }
                }
            } else {
                bookedCars = "No Cars Booked !";
            }
            String[] one_s_Record = {(i + 1) + "", "" + ID, CNIC, Name, ContactNo, bookedCars, Bill + ""};
            tablemodel.addRow(one_s_Record);
        }
    }

    private void addActionListeners() {
        SearchID_Button.addActionListener(this);
        SearchName_Button.addActionListener(this);
        Remove_Button.addActionListener(this);
        Add_Button.addActionListener(this);
        Update_Button.addActionListener(this);
        Back_Button.addActionListener(this);
        Logout_Button.addActionListener(this);
        ClearBill_Button.addActionListener(this);
        ViewBill_Button.addActionListener(this);
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Search ID": {
                String id = SearchID_TextField.getText().trim();
                if (!id.isEmpty()) {
                    if (Customer.isIDvalid(id)) {
                        Customer co = Customer.SearchByID(Integer.parseInt(id));
                        if (co != null) {
                            JOptionPane.showMessageDialog(null, co.toString());
                            SearchID_TextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Required person not found");
                            SearchID_TextField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID !");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Enter ID first !");
                }
            }
            break;
            case "Search Name": {
                String name = SearchName_TextField.getText().trim();
                if (!name.isEmpty()) {
                    if (Customer.isNameValid(name)) {
                        ArrayList<Customer> customerArrayList = Customer.SearchByName(name);
                        String record = "";
                        for (int i = 0; i < customerArrayList.size(); i++) {
                            record += customerArrayList.get(i).toString() + "\n";
                        }
                        if (!customerArrayList.isEmpty()) {
                            JOptionPane.showMessageDialog(null, record);
                            SearchName_TextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Required person not found");
                            SearchName_TextField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Name !");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Enter Name first !");
                }
            }
            break;
            case "Add": {
                Parent_JFrame.getMainFrame().setEnabled(false);
                Customer_Add aco = new Customer_Add();
                aco.frame.setVisible(true);
            }
            break;
            case "Remove": {
                Parent_JFrame.getMainFrame().setEnabled(false);
                new Customer_Remove().frame.setVisible(true);
            }
            break;
            case "Update": {
                Parent_JFrame.getMainFrame().setEnabled(false);
                new Customer_Update().frame.setVisible(true);
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
                JFrame frame = Runner.getFrame();
                Login login = new Login();
                JPanel panel = login.getMainPanel();
                frame.add(panel);
                frame.setVisible(true);
            }
            break;
            case "Clear Bill": {
                ArrayList<Customer> View = Customer.View(); // Creating an arrayList that contains Objects of all Customers
                if (!View.isEmpty()) {
                    ArrayList<String> IDsArray = new ArrayList<>(0);
                    for (int i = 0; i < View.size(); i++) { // getting IDs of all the Customers with Bill > 0
                        if (View.get(i).getBill() != 0) {
                            IDsArray.add(View.get(i).getID() + "");
                        }
                    }
                    Object showInputDialog = JOptionPane.showInputDialog(null, "Select ID for Customer whose bill you want to clear.", "Clear Bill",
                            JOptionPane.PLAIN_MESSAGE, null, IDsArray.toArray(), null);
                    System.out.println(showInputDialog);

                    if (showInputDialog != null) {
                        Customer customer = Customer.SearchByID((Integer.parseInt(showInputDialog + "")));

                        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "You are about to clear the balance for the following Customer\n"
                                + customer + "\nAre you sure you want to continue ?", "Clear Bill Confirmation",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                        if (showConfirmDialog == 0) {
                            customer.setBill(0);
                            customer.Update();
                            Parent_JFrame.getMainFrame().getContentPane().removeAll();
                            Customer_Details cd = new Customer_Details();
                            Parent_JFrame.getMainFrame().add(cd.getMainPanel());
                            Parent_JFrame.getMainFrame().getContentPane().revalidate();
                            JOptionPane.showMessageDialog(null, "Bill Successfully Cleared !");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No Customer Currently Registered !");
                }
            }
            break;
            case "View Bill": {
                ArrayList<Customer> customerList = Customer.View();
                if (!customerList.isEmpty()) {
                    String[] customerIDs = new String[customerList.size()];
                    for (int i = 0; i < customerList.size(); i++) {
                        customerIDs[i] = String.valueOf(customerList.get(i).getID());
                    }

                    String selectedID = (String) JOptionPane.showInputDialog(
                            null,
                            "Select Customer ID to View Bill:",
                            "View Bill",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            customerIDs,
                            (customerIDs.length > 0 ? customerIDs[0] : null) // Default selection if available
                    );

                    if (selectedID != null) {
                        try {
                            int id = Integer.parseInt(selectedID);
                            Customer customer = Customer.SearchByID(id);
                            if (customer != null) {
                                new Customer_Bill(customer); // Open the bill page for the customer
                            } else {
                                JOptionPane.showMessageDialog(null, "Customer not found!");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid ID format!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No customers available to view bills!");
                }
            }
            break;
        }
    }
}