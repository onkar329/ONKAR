package GUI;
import BackendCode.CarOwner;
import BackendCode.Car;
import BackendCode.Customer;
import BackendCode.Person;
import BackendCode.Booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill extends JFrame implements ActionListener {

    private JTextPane billTextPane;
    private JButton printButton, backButton;

    public Bill(Customer customer, Car car, int totalAmount) {
        setTitle("Customer Bill");
        setSize(600, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        billTextPane = new JTextPane();
        billTextPane.setContentType("text/html"); // use HTML for styling
        billTextPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(billTextPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        printButton = new JButton("Print Bill");
        backButton = new JButton("Back");

        printButton.addActionListener(this);
        backButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        String billHtml = generateBillHTML(customer, car, totalAmount);
        billTextPane.setText(billHtml);
        setVisible(true);
    }

    Bill(Object selectedCustomer, Object selectedCar, int totalAmount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String generateBillHTML(Customer customer, Car car, int totalAmount) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = sdf.format(new Date());

        return "<html><body style='font-family:sans-serif;padding:10px;'>"
                + "<h2 style='text-align:center;'>Rent-A-Car Management System</h2>"
                + "<hr/>"
                + "<p><strong>Date:</strong> " + currentDate + "</p>"
                + "<p><strong>Customer ID:</strong> " + customer.getID() + "</p>"
                + "<p><strong>Customer Name:</strong> " + customer.getName() + "</p>"
                + "<p><strong>CNIC:</strong> " + customer.getCNIC() + "</p>"
                + "<hr/>"
                + "<h3>Car Details</h3>"
                + "<p><strong>Car ID:</strong> " + car.getID() + "</p>"
                + "<p><strong>Car Name:</strong> " + car.getName() + "</p>"
                + "<hr/>"
                + "<h3>Total Amount: Rs. " + totalAmount + "</h3>"
                + "<br/><p style='text-align:center;'>Thank you for choosing Rent-A-Car!</p>"
                + "</body></html>";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == printButton) {
            try {
                boolean complete = billTextPane.print();
                if (complete) {
                    JOptionPane.showMessageDialog(this, "Bill printed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Printing cancelled.");
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Print failed: " + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            dispose();
        }
    }
}
