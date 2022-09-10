// Danish Mujeeb
// 501085629
import java.util.HashMap;
public class Shoes extends Product {

    // Declares a HashMap
    private HashMap<String, Integer> options = new HashMap<String, Integer>();

    // This constructur uses super to access the constructor in the Product class
    // All of the possible options and their associated stocks are put in the HashMap
    // The product options are the keys, while the stocks are the values
    public Shoes(String name, String id, double price, int sixBrownStock, int sevenBrownStock,
    int eightBrownStock, int nineBrownStock, int tenBrownStock, int sixBlackStock, int sevenBlackStock,
    int eightBlackStock, int nineBlackStock, int tenBlackStock)
    {
        super(name, id, price, 1, Category.CLOTHING);
        options.put("6 Brown", sixBrownStock);
        options.put("7 Brown", sevenBrownStock);
        options.put("8 Brown", eightBrownStock);
        options.put("9 Brown", nineBrownStock);
        options.put("10 Brown", tenBrownStock);
        options.put("6 Black", sixBlackStock);
        options.put("7 Black", sevenBlackStock);
        options.put("8 Black", eightBlackStock);
        options.put("9 Black", nineBlackStock);
        options.put("10 Black", tenBlackStock);
    }

    // Goes through the keys in the HashMap to see if the productOptions string matches any of the keys
    public boolean validOptions(String productOptions)
    {
        if (productOptions == null)
			return false;
        for (String key: options.keySet())
            if (productOptions.equals(key))
  	            return true;
        return false;
    }
  
    // Goes through the keys in the HashMap and checks if the productOptions string matches any of the keys
    // If it matches the value associated with that key gets returned
    public int getStockCount(String productOptions)
	{
        for (String key: options.keySet())
            if (productOptions.equals(key))
                return options.get(key);
        return 0;
	}
  
    // Goes through the keys in the HashMap and checks if the productOptions string matches any of the keys
    // If it matches the value associated with that key gets updated to the value of stockCount
    public void setStockCount(int stockCount, String productOptions)
	{
        
        for (String key: options.keySet())
            if (productOptions.equals(key))
                options.put(key, stockCount);
	}
  
    // Goes through the keys in the HashMap and checks if the productOptions string matches any of the keys
    // If it matches the value associated with that key gets reduced by 1
    public void reduceStockCount(String productOptions)
	{
        for (String key: options.keySet())
            if (productOptions.equals(key))
                options.put(key, options.get(key)-1);
	}
    
    // Uses the print method in the Product class to print some of the instance variables 
    public void print()
    {
  	    super.print();
    }

}
