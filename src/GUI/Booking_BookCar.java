package GUI;

import BackendCode.Booking;
import BackendCode.Car;
import BackendCode.Customer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class Booking_BookCar extends JFrame {

    JButton Book_Button, Cancel_Button;
    JComboBox<String> CarDropdown, CustomerDropdown;
    JPanel CarPanel, CustomerPanel, ButtonPanel;
    JLabel CarIDValidity_Label, CustomerIDValidity_Label;
    JPanel CarDetails_Panel, CustomerDetails_Panel;

    private Car car;
    private Customer customer;

    public Booking_BookCar() {
        super("Book Car");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(new Dimension(360, 360));
        setResizable(false);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Parent_JFrame.getMainFrame().setEnabled(true);
                dispose();
            }
        });

        CarPanel = new JPanel();
        CustomerPanel = new JPanel();
        ButtonPanel = new JPanel();

        CarPanel.setLayout(new BoxLayout(CarPanel, BoxLayout.Y_AXIS));
        CustomerPanel.setLayout(new BoxLayout(CustomerPanel, BoxLayout.Y_AXIS));
        ButtonPanel.setLayout(new FlowLayout());

        Book_Button = new JButton("Book");
        Cancel_Button = new JButton("Cancel");

        CarDropdown = new JComboBox<>();
        CarDropdown.setMaximumRowCount(2);
    
        CustomerDropdown = new JComboBox<>();
        CustomerDropdown.setMaximumRowCount(3);
        
        CarDetails_Panel = new JPanel(new GridLayout(0, 1));
        CustomerDetails_Panel = new JPanel(new GridLayout(0, 1));
        
       


        CarPanel.add(new JLabel("Select Car:"));
        CarPanel.add(CarDropdown);
        CarPanel.add(CarDetails_Panel);
        

        CustomerPanel.add(new JLabel("Select Customer:"));
        CustomerPanel.add(CustomerDropdown);
        CustomerPanel.add(CustomerDetails_Panel);
        

        ButtonPanel.add(Book_Button);
        ButtonPanel.add(Cancel_Button);

        add(CarPanel);
        add(CustomerPanel);
        add(ButtonPanel);

        loadCarData();
        loadCustomerData();

        CarDropdown.addActionListener(e -> updateCarDetails());
        CustomerDropdown.addActionListener(e -> updateCustomerDetails());

        Book_Button.addActionListener(e -> bookCar());
        Cancel_Button.addActionListener(e -> {
            Parent_JFrame.getMainFrame().setEnabled(true);
            dispose();
        });
    }
    
    private void loadCarData() {
        List<Car> cars = Car.View();
        cars.forEach(c -> CarDropdown.addItem(c.getID() + " - " + c.getModel()));
        if (!cars.isEmpty()) {
            CarDropdown.setSelectedIndex(0);
            updateCarDetails();
        }
    }

    private void loadCustomerData() {
        List<Customer> customers = Customer.View();
        customers.forEach(c -> CustomerDropdown.addItem(c.getID() + " - " + c.getName()));
        if (!customers.isEmpty()) {
            CustomerDropdown.setSelectedIndex(0);
            updateCustomerDetails();
        }
    }

    private void updateCarDetails() {
        String selectedCar = (String) CarDropdown.getSelectedItem();
        if (selectedCar != null) {
            int carID = Integer.parseInt(selectedCar.split(" - ")[0]);
            car = Car.SearchByID(carID);
            if (car != null) {
                CarDetails_Panel.removeAll();
                CarDetails_Panel.add(new JLabel("Model: " + car.getModel()));
                CarDetails_Panel.add(new JLabel("RegNo: " + car.getRegNo()));
                CarDetails_Panel.add(new JLabel("Type: " + car.getType()));
                CarDetails_Panel.add(new JLabel("Name: " + car.getName()));
                CarDetails_Panel.add(new JLabel("Color: " + car.getColour()));
                CarDetails_Panel.add(new JLabel("Status: " + (car.isRented() ? "Rented" : "Available")));
                CarDetails_Panel.add(new JLabel("Seats: " + car.getSeatingCapacity()));
                CarDetails_Panel.revalidate();
                CarDetails_Panel.repaint();
            }
        }
    }

    private void updateCustomerDetails() {
        String selectedCustomer = (String) CustomerDropdown.getSelectedItem();
        if (selectedCustomer != null) {
            int customerID = Integer.parseInt(selectedCustomer.split(" - ")[0]);
            customer = Customer.SearchByID(customerID);
            if (customer != null) {
                CustomerDetails_Panel.removeAll();
                CustomerDetails_Panel.add(new JLabel("Name: " + customer.getName()));
                CustomerDetails_Panel.add(new JLabel("ID: " + customer.getID()));
                CustomerDetails_Panel.revalidate();
                CustomerDetails_Panel.repaint();
            }
        }
    }

    private void bookCar() {
        if (car != null && customer != null) {
            if (car.isRented()) {
                JOptionPane.showMessageDialog(null, "This car is already booked!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Confirm booking for: " + car.getModel() + " by " + customer.getName(), "Confirm", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                Booking booking = new Booking(0, customer, car, System.currentTimeMillis(), 0);
                booking.Add();
                JOptionPane.showMessageDialog(null, "Car Successfully Booked!");
                updateCarDetails();
                Parent_JFrame.getMainFrame().setEnabled(true);
                dispose();
            }
        }
    }
}
