import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FoodOrderGUI extends JFrame implements ActionListener {
    private ArrayList<JCheckBox> mainDishCheckBoxes, drinkCheckBoxes, dessertCheckBoxes;
    private JRadioButton dineInRadioButton, takeOutRadioButton;
    private JRadioButton studentDiscountRadioButton, seniorDiscountRadioButton;
    private JButton calculateButton, printButton, clearButton;
    private JTextField paymentField;
    private JTextArea receiptArea;
    private FoodOrder foodOrder;

    public FoodOrderGUI() {
        mainDishCheckBoxes = new ArrayList<>();
        mainDishCheckBoxes.add(new JCheckBox("Hamburger - $5.00"));
        mainDishCheckBoxes.add(new JCheckBox("Cheeseburger - $5.50"));
        mainDishCheckBoxes.add(new JCheckBox("Chicken Sandwich - $6.00"));

        drinkCheckBoxes = new ArrayList<>();
        drinkCheckBoxes.add(new JCheckBox("Coke - $1.50"));
        drinkCheckBoxes.add(new JCheckBox("Pepsi - $1.50"));
        drinkCheckBoxes.add(new JCheckBox("Iced Tea - $2.00"));

        dessertCheckBoxes = new ArrayList<>();
        dessertCheckBoxes.add(new JCheckBox("Ice Cream - $3.00"));
        dessertCheckBoxes.add(new JCheckBox("Cake - $4.00"));
        dessertCheckBoxes.add(new JCheckBox("Pie - $4.00"));

        dineInRadioButton = new JRadioButton("Dine In");
        takeOutRadioButton = new JRadioButton("Take Out");

        studentDiscountRadioButton = new JRadioButton("Student Discount - 10%");
        seniorDiscountRadioButton = new JRadioButton("Senior Discount - 20%");

        ButtonGroup dineInTakeOutButtonGroup = new ButtonGroup();
        dineInTakeOutButtonGroup.add(dineInRadioButton);
        dineInTakeOutButtonGroup.add(takeOutRadioButton);

        ButtonGroup discountButtonGroup = new ButtonGroup();
        discountButtonGroup.add(studentDiscountRadioButton);
        discountButtonGroup.add(seniorDiscountRadioButton);

        calculateButton = new JButton("Calculate Total");
        printButton = new JButton("Print Receipt");
        clearButton = new JButton("Clear All");

        paymentField = new JTextField(10);

        receiptArea = new JTextArea(20, 40);
        receiptArea.setEditable(false);

        JPanel mainDishPanel = new JPanel(new GridLayout(mainDishCheckBoxes.size(), 1));
        mainDishPanel.setBorder(BorderFactory.createTitledBorder("Main Dish"));
        for (JCheckBox mainDishCheckBox : mainDishCheckBoxes) {
            mainDishPanel.add(mainDishCheckBox);
        }

        JPanel drinkPanel = new JPanel(new GridLayout(drinkCheckBoxes.size(), 1));
        drinkPanel.setBorder(BorderFactory.createTitledBorder("Drink"));
        for (JCheckBox drinkCheckBox : drinkCheckBoxes) {
            drinkPanel.add(drinkCheckBox);
        }

        JPanel dessertPanel = new JPanel(new GridLayout(dessertCheckBoxes.size(), 1));
        dessertPanel.setBorder(BorderFactory.createTitledBorder("Dessert"));
        for (JCheckBox dessertCheckBox : dessertCheckBoxes) {
            dessertPanel.add(dessertCheckBox);
        }

        JPanel dineInTakeOutPanel = new JPanel(new GridLayout(1, 2));
        dineInTakeOutPanel.setBorder(BorderFactory.createTitledBorder("Dine In/Take Out"));
        dineInTakeOutPanel.add(dineInRadioButton);
        dineInTakeOutPanel.add(takeOutRadioButton);

        JPanel discountPanel = new JPanel(new GridLayout(1, 2));
        discountPanel.setBorder(BorderFactory.createTitledBorder("Discount"));
        discountPanel.add(studentDiscountRadioButton);
        discountPanel.add(seniorDiscountRadioButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);
        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);

        JPanel paymentPanel = new JPanel(new FlowLayout());
        paymentPanel.add(new JLabel("Payment: "));
        paymentPanel.add(paymentField);

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        receiptPanel.add(paymentPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(new JLabel("Main Dish:"));
        mainPanel.add(mainDishPanel);
        mainPanel.add(new JLabel("Drink:"));
        mainPanel.add(drinkPanel);
        mainPanel.add(new JLabel("Dessert:"));
        mainPanel.add(dessertPanel);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 1));
        optionsPanel.add(dineInTakeOutPanel);
        optionsPanel.add(discountPanel);

        JPanel mainPanelWrapper = new JPanel(new BorderLayout());
        mainPanelWrapper.add(new JLabel("Select your order: "), BorderLayout.NORTH);
        mainPanelWrapper.add(mainPanel, BorderLayout.CENTER);
        mainPanelWrapper.add(optionsPanel, BorderLayout.SOUTH);

        add(mainPanelWrapper, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(receiptPanel, BorderLayout.EAST);

        foodOrder = new FoodOrder();

        calculateButton.addActionListener(this);
        printButton.addActionListener(this);
        clearButton.addActionListener(this);

        setTitle("Food Order GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void showReceiptWindow(String receipt) {
        JFrame receiptWindow = new JFrame("Order Receipt");
        receiptWindow.setLayout(new BorderLayout());

        String restaurantName = "Kainan ni Juan";
        String place = "Tarlac";

        String orderDetails = receipt.replaceAll("\n", "<br>");

        String htmlReceipt = "<html><body>"
                + "<div style='text-align:center;'>"
                + "<h2>" + restaurantName + "</h2>"
                + "<p>" + place + "</p>"
                + "<h3>Order Receipt</h3>"
                + "<hr>"
                + orderDetails
                + "<hr>"
                + "<h4>Thank you for buying!</h4>"
                + "</div>"
                + "</body></html>";

        JLabel receiptLabel = new JLabel(htmlReceipt);
        receiptLabel.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(receiptLabel);
        receiptWindow.add(scrollPane, BorderLayout.CENTER);

        receiptWindow.pack();
        receiptWindow.setLocationRelativeTo(null);
        receiptWindow.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            foodOrder = new FoodOrder();
            for (JCheckBox mainDishCheckBox : mainDishCheckBoxes) {
                if (mainDishCheckBox.isSelected()) {
                    if (mainDishCheckBox.getText().equals("Hamburger - $5.00")) {
                        foodOrder.addItem(new MainDish("Hamburger", 5.00));
                    } else if (mainDishCheckBox.getText().equals("Cheeseburger - $5.50")) {
                        foodOrder.addItem(new MainDish("Cheeseburger", 5.50));
                    } else if (mainDishCheckBox.getText().equals("Chicken Sandwich - $6.00")) {
                        foodOrder.addItem(new MainDish("Chicken Sandwich", 6.00));
                    }
                }
            }
            for (JCheckBox drinkCheckBox : drinkCheckBoxes) {
                if (drinkCheckBox.isSelected()) {
                    if (drinkCheckBox.getText().equals("Coke - $1.50")) {
                        foodOrder.addItem(new Drink("Coke", 1.50));
                    } else if (drinkCheckBox.getText().equals("Pepsi - $1.50")) {
                        foodOrder.addItem(new Drink("Pepsi", 1.50));
                    } else if (drinkCheckBox.getText().equals("Iced Tea - $2.00")) {
                        foodOrder.addItem(new Drink("Iced Tea", 2.00));
                    }
                }
            }
            for (JCheckBox dessertCheckBox : dessertCheckBoxes) {
                if (dessertCheckBox.isSelected()) {
                    if (dessertCheckBox.getText().equals("Ice Cream - $3.00")) {
                        foodOrder.addItem(new Dessert("Ice Cream", 3.00));
                    } else if (dessertCheckBox.getText().equals("Cake - $4.00")) {
                        foodOrder.addItem(new Dessert("Cake", 4.00));
                    } else if (dessertCheckBox.getText().equals("Pie - $4.00")) {
                        foodOrder.addItem(new Dessert("Pie", 4.00));
                    }
                }
            }
            if (dineInRadioButton.isSelected()) {
                foodOrder.setDineIn(true);
            } else if (takeOutRadioButton.isSelected()) {
                foodOrder.setDineIn(false);
            }
            if (studentDiscountRadioButton.isSelected()) {
                foodOrder.setStudentDiscount(true);
            } else if (seniorDiscountRadioButton.isSelected()) {
                foodOrder.setSeniorDiscount(true);
            }
            double total = foodOrder.calculateTotal();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            receiptArea.setText("Total: $" + decimalFormat.format(total));
        } else if (e.getSource() == printButton) {
            double subtotal = foodOrder.calculateSubtotal();
            double total = foodOrder.calculateTotal();
            String orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            double payment;
            try {
                payment = Double.parseDouble(paymentField.getText());
                if (payment < total) {
                    throw new IllegalArgumentException("Payment is insufficient.");
                }
            } catch (NumberFormatException ex) {
                receiptArea.setText("Invalid payment amount.");
                return;
            } catch (IllegalArgumentException ex) {
                receiptArea.setText(ex.getMessage());
                return;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String receipt = "Food Order Receipt\n\n";
            receipt += "Main Dishes:\n";
            for (JCheckBox mainDishCheckBox : mainDishCheckBoxes) {
                if (mainDishCheckBox.isSelected()) {
                    receipt += mainDishCheckBox.getText() + "\n";
                }
            }
            receipt += "\nDrinks:\n";
            for (JCheckBox drinkCheckBox : drinkCheckBoxes) {
                if (drinkCheckBox.isSelected()) {
                    receipt += drinkCheckBox.getText() + "\n";
                }
            }
            receipt += "\nDesserts:\n";
            for (JCheckBox dessertCheckBox : dessertCheckBoxes) {
                if (dessertCheckBox.isSelected()) {
                    receipt += dessertCheckBox.getText() + "\n";
                }
            }
            receipt += "\nOptions:\n";
            if (dineInRadioButton.isSelected()) {
                receipt += "Dine In\n";
            } else {
                receipt += "Take Out\n";
            }
            receipt += "\nSubtotal: $" + decimalFormat.format(subtotal) + "\n";
            if (studentDiscountRadioButton.isSelected()) {
                receipt += "Student Discount - 10%\n";
            } else if (seniorDiscountRadioButton.isSelected()) {
                receipt += "Senior Discount - 20%\n";
            }
            receipt += "Total (after discount): $" + decimalFormat.format(total) + "\n";
            receipt += "Payment: $" + decimalFormat.format(payment) + "\n";
            receipt += "Change: $" + decimalFormat.format(payment - total) + "\n";
            receipt += "\nOrder Date: " + orderDate + "\n";
            receiptArea.setText(receipt);
            showReceiptWindow(receipt);
        } else if (e.getSource() == clearButton) {
            for (JCheckBox mainDishCheckBox : mainDishCheckBoxes) {
                mainDishCheckBox.setSelected(false);
            }
            for (JCheckBox drinkCheckBox : drinkCheckBoxes) {
                drinkCheckBox.setSelected(false);
            }
            for (JCheckBox dessertCheckBox : dessertCheckBoxes) {
                dessertCheckBox.setSelected(false);
            }
            dineInRadioButton.setSelected(false);
            takeOutRadioButton.setSelected(false);
            studentDiscountRadioButton.setSelected(false);
            seniorDiscountRadioButton.setSelected(false);
            paymentField.setText("");
            receiptArea.setText("");
        }
    }

    public static void main(String[] args) {
        new FoodOrderGUI();
    }
}
