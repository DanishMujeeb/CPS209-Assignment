// Danish Mujeeb
// 501085629

/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product 
{
  private String author;
  private String title;
  private int year;
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  // Uses the construcytor in the Product class to intialize some instance variab;es
  // Also initializes additional Book instance variables
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	// Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	// Set category to BOOKS 
    super(name, id, price, 100000, Category.BOOKS);
    this.paperbackStock = paperbackStock;
    this.hardcoverStock = hardcoverStock;
    this.author = author;
    this.title = title;
    this.year = year;
  }
    
  // If the productOptions string is equal to Paperback, Hardcover or EBook, then it is a valid product option
  // Otherwise it is an invalid productOption and the method will return false  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
    if (productOptions == null)
			return false;
    else if (productOptions.equals("Paperback") || productOptions.equals("Hardcover") || productOptions.equals("EBook"))
  	  return true;
    return false;
  }
  
  // Overrides the getStockCount() method in the super class and returns the stock associated with that product option
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.
    if (productOptions.equals("Paperback"))
      return this.paperbackStock;
    else if (productOptions.equals("Hardcover"))
      return this.hardcoverStock;
  	return super.getStockCount(productOptions);
	}
   
  // Overrides the setStockCount() method in the super class and sets the stock associated with that product option
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equals("Paperback"))
      this.paperbackStock = stockCount;
    else if(productOptions.equals("Hardcover"))
      this.hardcoverStock = stockCount;
    else
      super.setStockCount(stockCount, productOptions);
	}
  
  //Overrides the reduceStockCount() method in the super class and reduces the stock associated with that product option by 1
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equals("Paperback"))
      this.paperbackStock--;
    else if(productOptions.equals("Hardcover"))
      this.hardcoverStock--;
    else
      super.setStockCount(super.getStockCount(productOptions), productOptions);
	}
  
  // Uses the print method in the Product class to print some instance variables
  // Also prints additional Book instance variables
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
    super.print();
    System.out.print(" Book Title: "+this.title+" Author: "+this.author);
  }
}
