// Danish Mujeeb
// 501085629

public class CartItem {
    
    // Declares private instance variables 
    private Product prod;
    private String productOptions;
    
    // Initializes the prod and productOptions instance variables
    public CartItem(Product prod, String productOptions)
    {
        this.prod = prod;
        this.productOptions = productOptions;
    }

    // Returns the Product object 
    public Product getProd()
    {
        return this.prod;
    }

    // Returns the productOptions string
    public String getProductOptions()
    {
        return this.productOptions;
    }
}
