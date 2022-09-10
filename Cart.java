// Danish Mujeeb
// 501085629

import java.util.ArrayList;
public class Cart { 

    // Declares a private ArrayList to store CartItem objects
    private ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

    // Returns the cartItems ArrayList
    public ArrayList<CartItem> getCartItems()
    {
        return this.cartItems;  
    }

    // Adds a CartItem object to the cartItems ArrayList 
    public void addCartItems(CartItem cartItem)
    {
        this.cartItems.add(cartItem);
    }

    // Removes a CartItem object from the cartItems ArrayList 
    public void removeCartItems(CartItem cartItem)
    {
        this.cartItems.remove(cartItem);
    }

    // Empties the cartItems ArrayList
    public void emptyCart()
    {
        this.cartItems.clear();
    }
}
