import java.util.ArrayList;

public class FoodOrder {
    private ArrayList<MenuItem> items;
    private boolean isDineIn;
    private boolean hasStudentDiscount;
    private boolean hasSeniorDiscount;

    public FoodOrder() {
        items = new ArrayList<>();
        isDineIn = false;
        hasStudentDiscount = false;
        hasSeniorDiscount = false;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setDineIn(boolean isDineIn) {
        this.isDineIn = isDineIn;
    }

    public void setStudentDiscount(boolean hasStudentDiscount) {
        this.hasStudentDiscount = hasStudentDiscount;
    }

    public void setSeniorDiscount(boolean hasSeniorDiscount) {
        this.hasSeniorDiscount = hasSeniorDiscount;
    }

    public double calculateTotal() {
        double total = 0;

        for (MenuItem item : items) {
            total += item.getPrice();
        }

        if (hasStudentDiscount) {
            total *= 0.1;
        } else if (hasSeniorDiscount) {
            total *= 0.2;
        }

        return total;
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (MenuItem item : items) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }
}
