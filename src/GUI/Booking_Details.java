package GUI;

import BackendCode.Booking;
import BackendCode.Car;
import BackendCode.Customer;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Booking_Details {

    private static DefaultTableModel tablemodel; // it is made static so that it can be accessed in add GUI class to update the Jtable when a new record is added

    private static JButton SearchCustomerID_Button, SearchCarRegNo_Button,
            BackButton, LogoutButton, BookCar_Button, UnbookCar_Button;
    private static JTextField CustomerID_TextField, CarRegNo_TextField;
    private static JScrollPane jScrollPane1;
    private static JTable jTable1;
    private JPanel MainPanel;

    public Booking_Details() {
        MainPanel = new JPanel();
        Parent_JFrame.getMainFrame().setTitle("Booking Details - Rent-A-Car Management System");
        MainPanel.setLayout(new AbsoluteLayout());
        MainPanel.setMinimumSize(new Dimension(1366, 730));

        SearchCustomerID_Button = new JButton("Search by Customer ID");
        SearchCarRegNo_Button = new JButton("Search by Car RegNo");
        BackButton = new JButton("Back");
        LogoutButton = new JButton("Logout");
        BookCar_Button = new JButton("Book");
        UnbookCar_Button = new JButton("Unbook");

        // Set modern background colors for buttons
        SearchCustomerID_Button.setBackground(new java.awt.Color(52, 152, 219)); // Blue
        SearchCarRegNo_Button.setBackground(new java.awt.Color(46, 204, 113));  // Green
        BackButton.setBackground(new java.awt.Color(241, 196, 15));             // Yellow
        LogoutButton.setBackground(new java.awt.Color(231, 76, 60));            // Red
        BookCar_Button.setBackground(new java.awt.Color(155, 89, 182));         // Purple
        UnbookCar_Button.setBackground(new java.awt.Color(26, 188, 156));       // Turquoise

        // Ensure the buttons are opaque to show the background color
        SearchCustomerID_Button.setOpaque(true);
        SearchCarRegNo_Button.setOpaque(true);
        BackButton.setOpaque(true);
        LogoutButton.setOpaque(true);
        BookCar_Button.setOpaque(true);
        UnbookCar_Button.setOpaque(true);

        CustomerID_TextField = new JTextField();
        CarRegNo_TextField = new JTextField();

        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        String[] columns = {"Sr#", "ID", "Customer ID+Name", "Car Name", "Rent Time", "Return Time"};
        tablemodel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        jTable1 = new JTable(getTablemodel());
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Modern Table Design
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)); // Modern font
        jTable1.setRowHeight(30); // Increase row height for better spacing
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new java.awt.Color(230, 230, 230)); // Light gray grid color

        // Alternating row colors
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new java.awt.Color(255, 255, 255) : new java.awt.Color(245, 245, 245)); // White and light gray
                }
                return c;
            }
        });

        // Customize header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Bold header font
        header.setBackground(new java.awt.Color(52, 152, 219)); // Blue header background
        header.setForeground(new java.awt.Color(255, 255, 255)); // White header text
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Increase header height

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adjust column widths
        jTable1.getColumnModel().getColumn(0).setMinWidth(80);
        jTable1.getColumnModel().getColumn(1).setMinWidth(80);
        jTable1.getColumnModel().getColumn(2).setMinWidth(400);
        jTable1.getColumnModel().getColumn(3).setMinWidth(300);
        jTable1.getColumnModel().getColumn(4).setMinWidth(230);
        jTable1.getColumnModel().getColumn(5).setMinWidth(235);

        jTable1.getTableHeader().setReorderingAllowed(false);

        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(jTable1);
        jTable1.setFillsViewportHeight(true);

        ArrayList<Booking> Booking_objects = Booking.View();
        for (int i = 0; i < Booking_objects.size(); i++) {
            int ID = Booking_objects.get(i).getID();
            String customer_ID_Name = Booking_objects.get(i).getCustomer().getID()
                    + ": " + Booking_objects.get(i).getCustomer().getName();
            String carName = Booking_objects.get(i).getCar().getName();
            String carID = Booking_objects.get(i).getCar().getID() + "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a dd-MM-yyyy");
            Date rentime = new Date(Booking_objects.get(i).getRentTime());
            String rentTime = dateFormat.format(rentime);

            long returnTime_ = Booking_objects.get(i).getReturnTime();
            String returnTime;
            if (returnTime_ != 0) {
                Date returntime = new Date(returnTime_);
                returnTime = dateFormat.format(returntime);
            } else {
                returnTime = "Not returned yet !";
            }

            String[] one_s_Record = {((i + 1) + ""), "" + ID, customer_ID_Name, carID + ": " + carName, rentTime, returnTime};
            tablemodel.addRow(one_s_Record);
        }

        MainPanel.add(jScrollPane1, new AbsoluteConstraints(10, 60, 1330, 550));
        MainPanel.add(BackButton, new AbsoluteConstraints(1106, 625, 100, 22));
        MainPanel.add(LogoutButton, new AbsoluteConstraints(1236, 625, 100, 22));
        MainPanel.add(BookCar_Button, new AbsoluteConstraints(10, 625, 130, 22));
        MainPanel.add(UnbookCar_Button, new AbsoluteConstraints(160, 625, 130, 22));

        MainPanel.add(SearchCarRegNo_Button, new AbsoluteConstraints(10, 15, 160, 22));
        MainPanel.add(CarRegNo_TextField, new AbsoluteConstraints(185, 15, 240, 22));
        MainPanel.add(SearchCustomerID_Button, new AbsoluteConstraints(440, 15, 180, 22));
        MainPanel.add(CustomerID_TextField, new AbsoluteConstraints(635, 15, 240, 22));

        SearchCustomerID_Button.addActionListener(new Booking_Details_ActionListener());
        SearchCarRegNo_Button.addActionListener(new Booking_Details_ActionListener());
        BackButton.addActionListener(new Booking_Details_ActionListener());
        LogoutButton.addActionListener(new Booking_Details_ActionListener());
        BookCar_Button.addActionListener(new Booking_Details_ActionListener());
        UnbookCar_Button.addActionListener(new Booking_Details_ActionListener());
    }

    public static DefaultTableModel getTablemodel() {
        return tablemodel;
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private class Booking_Details_ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()) {

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
                case "Book": {
                    if (!Booking.getUnbookedCars().isEmpty()) {
                        Parent_JFrame.getMainFrame().setEnabled(false);
                        Booking_BookCar ac = new Booking_BookCar();
                        ac.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No UnBooked Cars are available !");
                    }
                }
                break;
                case "Unbook": {
                    if (!Booking.getBookedCars().isEmpty()) {
                        Parent_JFrame.getMainFrame().setEnabled(false);
                        Booking_UnBookCar ac = new Booking_UnBookCar();
                        ac.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No Booked Cars found !");
                    }
                }
                break;
                case "Search by Customer ID": {
                    String customerID = CustomerID_TextField.getText().trim();
                    if (!customerID.isEmpty()) {
                        if (Customer.isIDvalid(customerID)) {
                            Customer customer = Customer.SearchByID(Integer.parseInt(customerID));
                            if (customer != null) {
                                ArrayList<Booking> bookings = Booking.SearchByCustomerID(Integer.parseInt(customerID));
                                if (!bookings.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, bookings.toString());
                                } else {
                                    JOptionPane.showMessageDialog(null, "This Customer has not booked any cars yet !");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Customer ID not found !");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Customer ID !");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Customer ID first !");
                    }
                    CustomerID_TextField.setText("");
                }
                break;
                case "Search by Car RegNo": {
                    String carRegNo = CarRegNo_TextField.getText().trim();
                    if (!carRegNo.isEmpty()) {
                        if (Car.isRegNoValid(carRegNo)) {
                            Car car = Car.SearchByRegNo(carRegNo);
                            if (car != null) {
                                ArrayList<Booking> bookings = Booking.SearchByCarRegNo(carRegNo);
                                if (!bookings.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, bookings.toString());
                                } else {
                                    JOptionPane.showMessageDialog(null, "This Car is not booked yet !");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Registeration no. not found !");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Registeration no !");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Car Registeration No first !");
                    }
                    CarRegNo_TextField.setText("");
                }
                break;
            }
        }
    }
}