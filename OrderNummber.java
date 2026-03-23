public class OrderNummber {
    private int orderNumm;
    private boolean orderCompleted;
    private int productId; // NEU
    private int quantity;  // NEU
    private String productName;

    // Konstruktor erweitert
    public OrderNummber(int orderNumm, boolean orderCompleted, int productId, int quantity, String productName) {
        this.orderNumm = orderNumm;
        this.orderCompleted = orderCompleted;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
    }

    // Getter und Setter (Auszug)
    public int getOrderNumm() { return orderNumm; }
    public boolean getOrderCompleted() { return orderCompleted; }

    public void setOrderCompleted(boolean orderCompleted) { this.orderCompleted = orderCompleted; }

    public int getProductId() {return  productId; }
    public void setProductId(int productId) { this.productId = productId;}

    public int getQuantity() {return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) {this.productName = productName; }

}