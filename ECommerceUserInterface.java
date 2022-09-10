// Danish Mujeeb
// 501085629

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			try{
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks(); 
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();	
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();	
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();	
				}

				// It gets the address and name of the custmer and checks if they are valid
				// If they are it creates a new registered customer
				else if (action.equalsIgnoreCase("NEWCUST"))	
				{
					String name = "";
					String address = "";
					
					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);

				}

				// Ships an order to a customer
				// It does this by checking if the entered order number is valid
				// If it is then the order will be shipped to that customer
				else if (action.equalsIgnoreCase("SHIP"))	
				{
						String orderNumber = "";
			
						System.out.print("Order Number: ");
						// Get order number from scanner
						if (scanner.hasNextLine())
							orderNumber = scanner.nextLine();
					
						// Ship order to customer (see ECommerceSystem for the correct method to use
						ProductOrder proOrd = null;
						proOrd = amazon.shipOrder(orderNumber);
						proOrd.print();

				}

				// After checking for a valid customerId
				// All the current orders and shipped orders for that customer id are displayed
				else if (action.equalsIgnoreCase("CUSTORDERS")) 
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner

					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);
	
				}

				// Orders a product for a certain customer
				// It does this by getting the customer and product id from the user and checking if they exist
				// If they don't an error message will be displayed
				else if (action.equalsIgnoreCase("ORDER")) 
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner

					if (scanner.hasNextLine())
						productId = scanner.nextLine();
					
					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner

					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					
					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
					// Print Order Number string returned from method in ECommerceSystem
					
					String ord = "";
					ord = amazon.orderProduct(productId, customerId, "");
					System.out.println("Order #"+ord);
						
				}

				// Orders a book for a certain customer
				// It does this by getting the customerId, productId and productOptions from the user and checking if they exist
				// If they don't an error message will be displayed
				else if (action.equalsIgnoreCase("ORDERBOOK")) 
				{
					String productId = "";
					String customerId = "";
					String options = null;

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book forma and store in options string
					if (scanner.hasNextLine())
						options = scanner.nextLine();

					if (options.equals(""))// Helps with making sure the user doesn't order a book when entering order
						options = null;
					// Order product. Check for error mesage set in ECommerceSystem
					// Print order number string if order number is not null

					String ord = "";
					ord = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #"+ord);
				}

				// Orders shoes for a certain customer
				// It does this by getting the customerId, productId and productOptions from the user and checking if they exist
				// If they don't an error message will be displayed
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
				{
					String productId = "";
					String customerId = "";
					String options = null;
					
					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options	
					if (scanner.hasNextLine())
						options = scanner.nextLine();
					
					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine())
						options += " "+scanner.nextLine();

					if (options.equals(""))// Helps with making sure the user doesn't order a book when entering order
						options = null;
					
					//orders shoes
					String ord = "";
					ord = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #"+ord);
				}
				
				// Cancels an existing order, by first checking if a valid Order Number is inputted by the user
				else if (action.equalsIgnoreCase("CANCEL")) 
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					// cancel order. Check for error
					amazon.cancelOrder(orderNumber);
					
				}

				// Prints products by prices
				else if (action.equalsIgnoreCase("PRINTBYPRICE")) 
				{
					amazon.sortByPrice();
				}

				// Prints products by name (alphabetic)
				else if (action.equalsIgnoreCase("PRINTBYNAME")) 
				{
					amazon.sortByName();
				}

				// Sorts customers by name (alphabetic)
				else if (action.equalsIgnoreCase("SORTCUSTS")) 
				{
					amazon.sortCustomersByName();
				}

				// Adds a product to a customers cart
				// It does this by getting the productId, customerId and productOptions and calling the addCart method
				// If there is an invalid input an error will be displayed
				else if (action.equalsIgnoreCase("ADDTOCART"))
				{
					String productId = "";
					String customerId = "";
					String options = null;
					
					System.out.print("Product Id: ");
					// Gets product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Gets customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print(amazon.checkOptions(productId));// Calls the checkOptions method
					// Gets product options
					if (scanner.hasNextLine())
						options = scanner.nextLine();

					amazon.addToCart(productId, customerId, options);
				}

				// Removes a product from a customers cart
				// It does this by getting the productId, customerId and productOptions and calling the removeCart method
				// If there is an invalid input an error will be displayed
				else if (action.equalsIgnoreCase("REMCARTITEM"))
				{
					String productId = "";
					String customerId = "";
					
					System.out.print("Product Id: ");
					// Gets product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Gets customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					amazon.removeFromCart(productId, customerId);
				}

				// Gets the customerId to print the cart for that customer, if invalid id an error is displayed
				else if (action.equalsIgnoreCase("PRINTCART"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					// Gets customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					amazon.printCartItems(customerId);
				}

				// Creates a product order for all the products in a customers cart
				// Gets the customerId to do this, if it is invalid an error will be displayed
				else if (action.equalsIgnoreCase("ORDERITEMS"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					// Gets customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					amazon.orderItems(customerId);
				}

				// Prints how many times the products have been ordered from most to least
				else if(action.equalsIgnoreCase("STATS"))
				{
					amazon.printOrderStatistics();
				}

			}
			// The following catch statements catch the excptions that are thrown in ECommerceSystem
			// They then display the error message

			// Catches an UnknownCustomerException
			catch (UnknownCustomerException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches an UnknownProductException
			catch (UnknownProductException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches an InvalidProductOptionsException
			catch (InvalidProductOptionsException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches a ProductOutOfStockException
			catch (ProductOutOfStockException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches an InvalidCustomerNameException
			catch (InvalidCustomerNameException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches an InvalidCustomerAddressException
			catch (InvalidCustomerAddressException e)
			{
				System.out.println(e.getMessage());
			}

			// Catches an InvalidOrderNumberException
			catch (InvalidOrderNumberException e)
			{
				System.out.println(e.getMessage());
			}

			System.out.print("\n>");
		}
	}
}
