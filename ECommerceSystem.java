// Danish Mujeeb
// 501085629

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private Map<String, Product> products = new TreeMap<String, Product>(); // Declares a TreeMap called products
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();

    private Map<Product, Integer> stats = new HashMap<Product, Integer>(); // Declares a HashMap called stats
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing
    	// If you do the class Shoes bonus, you may add shoe products
    	ArrayList<Product> prods = new ArrayList<Product>();
      prods = readFile(); // Calls the readFile method and assigns what it returns to prods
    	
      for(Product p : prods)
        products.put(p.getId(), p); // Adds a key value pair to the products map
    	
    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin", new Cart()));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin", new Cart()));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine",  new Cart()));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach", new Cart()));
    }

    // Reads the products.txt file and creates Product and Book objects using the information in the file
    // The objects are then put in an ArrayList and this ArrayList then gets returned
    private ArrayList<Product> readFile()
    {
      ArrayList<Product> products = new ArrayList<Product>();

      try{
        File file = new File("products.txt");
        Scanner in = new Scanner(file);

        while(in.hasNextLine()) // Runs so long as the end of the file hasn't been reached
        {
          String id = generateProductId();
          Product.Category category = Product.Category.GENERAL; 
          String categoryString = in.nextLine();

          // The following code figures out what category the product belongs to
          // The category variable is then set to the correct Category type
          if(categoryString.equals("")) // Reads the next line in the file if categoryString is empty
            categoryString = in.nextLine();
          if(categoryString.equals("GENERAL"))
            category = Product.Category.GENERAL;
          else if(categoryString.equals("CLOTHING"))
            category = Product.Category.CLOTHING;
          else if(categoryString.equals("BOOKS"))
            category = Product.Category.BOOKS;
          else if(categoryString.equals("FURNITURE"))
            category = Product.Category.FURNITURE;
          else if(categoryString.equals("COMPUTERS"))
            category = Product.Category.COMPUTERS;

          String name = in.nextLine();
          Double price = Double.parseDouble(in.nextLine());
          int paperbackStock = 0;
          int hardcoverStock = 0;

          if(category != Product.Category.BOOKS) // Checks if the category is not BOOKS
          {
            int stock = Integer.parseInt(in.nextLine());
            products.add(new Product(name, id, price, stock, category));
            // Creates a Product object and adds it to the products ArrayList
            in.nextLine();
          }

          if(category == Product.Category.BOOKS) // Checks if the category is BOOKS
          {
            Scanner input = new Scanner(in.nextLine());
            paperbackStock = Integer.parseInt(input.next());
            hardcoverStock = Integer.parseInt(input.next());
            input.close();
            input = new Scanner(in.nextLine()); // Creates a new scanner object
            input.useDelimiter(":"); // Uses a delemiter to know where to break for the next word
            String title = input.next();
            String author = input.next();
            int year = Integer.parseInt(input.next());
            products.add(new Book(name, id, price, paperbackStock, hardcoverStock, title, author, year));
            // Creates a Book object and adds it to the products ArrayList
          }
        }
      } catch(IOException e) // Catches any IOExceptions
      {
        System.out.println(e.getMessage());
        System.exit(1); // Exists the program
      }

      return products; // Returns the ArrayList products
    }

    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    { 
    	for (Product p : products.values())
    		p.print();
    }
    
    // Print all products that are books 
    // That is all of the elements in the products map that have their category as BOOKS. 
    // See getCategory() method in class Product
    public void printAllBooks()
    {
    	for (Product p : products.values())
        if (p.getCategory() == Product.Category.BOOKS)
          p.print();
    }
    
    // Print all current orders i.e. all of the elementss in the orders ArrayList
    public void printAllOrders()
    {
      for (ProductOrder po : orders)
    	  po.print();
    }
    // Print all shipped orders i.e. all of the elements in the shippedOrders ArrayList
    public void printAllShippedOrders()
    {
    	for (ProductOrder so : shippedOrders)
    		so.print();
    }
    
    // Print all customers i.e. all of the elements in the customers ArrayList
    public void printCustomers()
    { 
      for (Customer c : customers)
        c.print();
    }

    // Prints all the items in a customers cart
    // If a user provides an invalid CustomerId an exception will be thrown
    public void printCartItems(String customerId)
    {
      // Checks if customerId exists, if it doesn't an exception is thrown
      boolean checkCustomer = false;
      Customer cust = null;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          cust = c;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException 
      
      // Goes through the cartItems ArrayList and prints all the items in the ArrayList
      ArrayList<CartItem> cItems;
      cItems = cust.getCart().getCartItems();
      for(int i=0;i<cItems.size();i++)
        cItems.get(i).getProd().print(); 
    }

    // Checks if they customerId is valid, if it is then all of the current and shipped orders will be displayed for that customer
    // If the customerId is invalid, then an error will be set
    public void printOrderHistory(String customerId)
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here

      boolean checkCustomer = false;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException 
    	
    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	// enter code here
      for (ProductOrder po : orders)
        if (po.getCustomer().getId().equals(customerId))
          po.print();
    	
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here
      for (ProductOrder so : shippedOrders)
        if (so.getCustomer().getId().equals(customerId))
          so.print();
    }
    
    // Checks if the customerId, productId and productOptions are valid
    // If these are invalid or the stock count for the prduct is 0, an error will be set
    public String orderProduct(String productId, String customerId, String productOptions)
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object
    	
      boolean checkCustomer = false;
      Customer cust = null;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          cust = c;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException 
      
    	// Check to see if product object with productId exists in the map key set  of products
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Product object 
    	
      boolean checkProduct = false;
      Product prod = null;
      if(products.keySet().contains(productId))
      {
        checkProduct = true;
        prod = products.get(productId);
      }
      if (!checkProduct)
        throw new UnknownProductException("Product "+productId+" Not found"); // Throws an UnknownProductException 

    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method validOptions()
    	// If options are not valid, set errMsg string and return null;

      if (!prod.validOptions(productOptions)) 
        throw new InvalidProductOptionsException("Product "+prod.getName()+" ProductId "+prod.getId()+" Invalid Options: "+productOptions); 
        // Throws an InvalidProductOptionsException 
    
    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
      

      if(prod.getStockCount(productOptions) == 0)
        throw new ProductOutOfStockException("No more stock available"); // Throws a ProductOutOfStockException

      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string

      ProductOrder prodOrd = new ProductOrder(generateOrderNumber(), prod, cust, productOptions);
      prod.reduceStockCount(productOptions);
      orders.add(prodOrd);
      if(stats.containsKey(prod)) // Checks if the Product object is already in the map
        stats.put(prod, stats.get(prod)+1); 
      else
        stats.put(prod, 1);
    	return prodOrd.getOrderNumber(); // replace this line

    }
    
    // Checks if name and address are valid, if they are a Customer object is created and added to the customers ArrayList
    // If either are invalid an error will be set
    public void createCustomer(String name, String address)
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter

      if (name == null || name.equals("")) 
        throw new InvalidCustomerNameException("Invalid Cutomer Name"); // Throws an InvalidCustomerNameException
    	
      if (address == null || address.equals("")) 
        throw new InvalidCustomerAddressException("Invalid Cutomer Address"); // Throws an InvalidCustomerAddressException
    	// Create a Customer object and add to array list
      customers.add(new Customer(generateCustomerId(), name, address, new Cart()));
    	
    }
    
    // Checks if the orderNumber exists, if it does, the order is shiped
    // If it doesn't an error will be set
    public ProductOrder shipOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the order
      boolean checkOrder = false;
      ProductOrder proOrd = null;
      for (ProductOrder po : orders)
    	  if (po.getOrderNumber().equals(orderNumber))
        {
          checkOrder = true;
          proOrd = po;
          break;
        }
      if (!checkOrder)
        throw new InvalidOrderNumberException("Order "+orderNumber+" Not Found"); // Throws an InvalidOrderNumberException 
      orders.remove(proOrd);
      shippedOrders.add(proOrd);
      return proOrd;
    }
    
    // Checks if orderNumber is valid, if it is then the order with that orderNumber will be cancelled
    // If it is invalid then an error will be set
    public void cancelOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	boolean checkOrder = false;
      ProductOrder proOrd = null;
      for (ProductOrder po : orders)
    	  if (po.getOrderNumber().equals(orderNumber))
        {
          checkOrder = true;
          proOrd = po;
          break;
        }
      if (!checkOrder)
        throw new InvalidOrderNumberException("Order "+orderNumber+" Not Found"); // Throws an InvalidOrderNumberException 
      orders.remove(proOrd);

    }
    
    // The products get sorted by increasing price and then get displayed
    public void sortByPrice()
    {
      ArrayList<Product> prods = new ArrayList<Product>();
      for(Product value : products.values())
        prods.add(value); // Puts all the values in the products map into an ArrayList

      Collections.sort(prods, new inner2()); // Uses the inner2 class to sort the products by price
      for(Product p : prods)
        p.print();
    }
    
    // The products get sorted alphabetically by product name and then get displayed
    public void sortByName()
    {
      ArrayList<Product> prods = new ArrayList<Product>();
      for(Product value : products.values())
        prods.add(value); // Puts all the values in the products map into an ArrayList

  	  Collections.sort(prods, new inner()); // Uses the inner class to sort the products alphabetically
      for(Product p : prods)
        p.print();
    }
        
    // The customers get sorted alphabetically by customer name
    public void sortCustomersByName()
    {
      Collections.sort(customers);
    }

    // Uses the Comparator interface to compare two product names
    class inner implements Comparator<Product>
    {
      public int compare(Product p1, Product p2)
      {
        return p1.getName().compareTo(p2.getName());
      }
    }

    // Uses the Comparator interface to compare two product prices
    class inner2 implements Comparator<Product>
    {
      public int compare(Product p1, Product p2)
      {
        if (p1.getPrice() > p2.getPrice())
          return 1;
        else if (p1.getPrice() < p2.getPrice())
          return -1;
        return 0;
      }
    }

    // Adds a product to a customers cart
    // If a user provids an invalid input an exception will be thrown
    public void addToCart(String productId, String customerId, String productOptions)
    {
      // Checks if customerId exists, if it doesn't an exception is thrown
      boolean checkCustomer = false;
      Customer cust = null;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          cust = c;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException   
      
      // Checks if productId exists, if it doesn't an exception is thrown
      boolean checkProduct = false;
      Product prod = null;
      if(products.keySet().contains(productId))
      {
        checkProduct = true;
        prod = products.get(productId);
      }
      if (!checkProduct)
        throw new UnknownProductException("Product "+productId+" Not found"); // Throws an UnknownProductException 

      // Checks if product option is valid, if it doesn't an exception is thrown
      if (!prod.validOptions(productOptions)) 
        throw new InvalidProductOptionsException("Product "+prod.getName()+" ProductId "+prod.getId()+" Invalid Options: "+productOptions); 
        // Throws an InvalidProductOptionsException 

      // Gets the customers cart and adds a CartItem object to the ArrayList in the Cart class
      cust.getCart().addCartItems(new CartItem(prod, productOptions));
    }

    // Removes a product from a customers cart
    // If a user provids an invalid input an exception will be thrown
    public void removeFromCart(String productId, String customerId)
    {
      // Checks if customerId exists, if it doesn't an exception is thrown
      boolean checkCustomer = false;
      Customer cust = null;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          cust = c;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException   
      
      // Checks if productId exists, if it doesn't an exception is thrown
      boolean checkProduct = false;
      if(products.keySet().contains(productId))
        checkProduct = true;

      if (!checkProduct)
        throw new UnknownProductException("Product "+productId+" Not found"); // Throws an UnknownProductException 
      
      // Checks if the Product object in the CartItem matches the productId
      // If it does this CartItem gets removed from the customers cart
      ArrayList<CartItem> cItems;
      cItems = cust.getCart().getCartItems();
      for(int i=0;i<cItems.size();i++) 
        if(cItems.get(i).getProd().getId().equals(productId))
          cust.getCart().removeCartItems(cItems.get(i));
    }

    // Orders each item that is in the customers cart
    // If a user provides an invalid CustomerId an exception will be thrown
    public void orderItems(String customerId)
    {
      // Checks if customerId exists, if it doesn't an exception is thrown
      boolean checkCustomer = false;
      Customer cust = null;
      for (Customer c : customers)
        if (c.getId().equals(customerId))
        {
          checkCustomer = true;
          cust = c;
          break;
        }
      if (!checkCustomer)
        throw new UnknownCustomerException("Customer "+customerId+" Not found"); // Throws an UnknownCustomerException   
      
      // Goes through the cartItems ArrayList and calls the orderProduct method 
      // This will make a ProductOrder for each CartItem in the ArrayList
      ArrayList<CartItem> cItems;
      cItems = cust.getCart().getCartItems();
      for(int i=0;i<cItems.size();i++) 
        orderProduct(cItems.get(i).getProd().getId(), customerId, cItems.get(i).getProductOptions());

      cust.getCart().emptyCart(); // Empties the cart
    }

    // Prints how many times a product has been ordered
    public void printOrderStatistics()
    {
      ArrayList<Product> list = new ArrayList<Product>();
      for (Product p : stats.keySet())
        list.add(p); // Puts all the values in the stats map into an ArrayList
      
      Collections.sort(list, new inner3()); // Uses the inner3 class to sort the products by times ordered
      for (Product p : list)
        System.out.printf("\nId: %-5s Name: %-15s Times Ordered: %-7s", p.getId(), p.getName(), stats.get(p));
    }

    // Uses the Comparator interface to compare product objects by how many times they were ordered
    class inner3 implements Comparator<Product>
    {
      public int compare(Product p1, Product p2)
      {
        if (stats.get(p1) > stats.get(p2))
          return -1;
        else if (stats.get(p1) < stats.get(p2))
          return 1;
        return 0;
      }
    }

    // Checks if the product is a Book, if it is, it displays the options for a Book object
    public String checkOptions(String productId)
    {
      if(products.keySet().contains(productId))
        if(products.get(productId).getCategory() == Product.Category.BOOKS)
          return "\nBook Options: Paperback HardCover EBook: ";

      return "\nProduct Options: ";
    }
}

// The following classes extend from class RuntimeException to create custom exceptions

// Creates an UnknownCustomerException class  
class UnknownCustomerException extends RuntimeException
{
  public UnknownCustomerException(){}

  // Uses the constructor in the super class to initialize the message
  public UnknownCustomerException(String message)
  {
    super(message);
  }
}

// Creates an UnknownProductException class  
class UnknownProductException extends RuntimeException
{
  public UnknownProductException(){}

  // Uses the constructor in the super class to initialize the message
  public UnknownProductException(String message)
  {
    super(message);
  }
}

// Creates an InvalidProductOptionsException class 
class InvalidProductOptionsException extends RuntimeException
{
  public InvalidProductOptionsException(){}

  // Uses the constructor in the super class to initialize the message
  public InvalidProductOptionsException(String message)
  {
    super(message);
  }
}

// Creates a ProductOutOfStockException class 
class ProductOutOfStockException extends RuntimeException
{
  public ProductOutOfStockException(){}

  // Uses the constructor in the super class to initialize the message
  public ProductOutOfStockException(String message)
  {
    super(message);
  }
}

// Creates an InvalidCustomerNameException class 
class InvalidCustomerNameException extends RuntimeException
{
  public InvalidCustomerNameException(){}

  // Uses the constructor in the super class to initialize the message
  public InvalidCustomerNameException(String message)
  {
    super(message);
  }
}

// Creates an InvalidCustomerAddressException class 
class InvalidCustomerAddressException extends RuntimeException
{
  public InvalidCustomerAddressException(){}

  // Uses the constructor in the super class to initialize the message
  public InvalidCustomerAddressException(String message)
  {
    super(message);
  }
}

// Creates an InvalidOrderNumberException class 
class InvalidOrderNumberException extends RuntimeException
{
  public InvalidOrderNumberException(){}

  // Uses the constructor in the super class to initialize the message
  public InvalidOrderNumberException(String message)
  {
    super(message);
  }
}