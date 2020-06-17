public class Product {

    private String name;
    private double price;
    private int amount;
    private Integer id;


    public Product(Integer id, String name, double price, int amount) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;

    }

    public Product(String name, double price, int amount) {

        this(null, name, price, amount);


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product {" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", id=" + id +
                "}";
    }

}
