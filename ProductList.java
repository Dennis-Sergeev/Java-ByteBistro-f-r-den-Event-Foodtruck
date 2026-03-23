import java.util.ArrayList;

public class ProductList
{
     ArrayList<Product> inventory = new ArrayList<>();

    public void initInventory()
    {
        inventory.add(new Product("Kebab", 3.20f, 30));
        inventory.add(new Product("Pizza", 3.99f, 12));
        inventory.add(new Product("Hamburger", 0.99f, 8));
        inventory.add(new Product("Fries", 1.49f, 22));
        inventory.add(new Product("Chicken Nuggets Bucket", 8.30f, 6));
        inventory.add(new Product("Coocked Potato", 2.00f, 123));
        inventory.add(new Product("Baklava", 2.89f, 16));
        inventory.add(new Product("Lahmacun", 1.67f, 14));
    }

    public void showInventory()
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            Product p = inventory.get(i);
            System.out.println("ID: " + i + " | " + p.getName() + " | " + p.getPrice() + "€ | Bestand: " + p.getStock());
        }
    }

    public float getPrice(int index)
    {
        Product p = inventory.get(index);
        return p.getPrice();
    }

    public int getStock(int index)
    {
        Product p = inventory.get(index);
        return p.getStock();
    }

    public void setStock(int index, int newStock)
    {
        Product p = inventory.get(index);
        p.setStock(newStock);
    }
}
