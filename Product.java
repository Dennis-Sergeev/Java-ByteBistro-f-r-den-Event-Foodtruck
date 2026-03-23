public class Product
{
    private String name;
    private float price;
    private int stock;

    //Konstruktor
    public Product(String name, float price, int stock)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    //Getter und Setter
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public void setPrice(float price)
    {
        this.price = price;
    }
    public float getPrice()
    {
        return price;
    }
    public void setStock(int stock)
    {
        this.stock = stock;
    }
    public int getStock()
    {
        return stock;
    }
}