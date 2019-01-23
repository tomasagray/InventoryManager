/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exceptions.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Product
{
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    public Product()
    {
        this( -1, "default", 0, 0.0, 0, 0 );
    }
    
    public Product(int id, String name, int inv, double price, int max, int min)
    {
        setProductID(id);
        setName(name);
        setInStock(inv);
        setPrice(price);
        setMax(max);
        setMin(min);
        associatedParts = new ArrayList();
    }
    
    /**
     * Copy constructor
     * @param p 
     */
    public Product( Product p )
    {
        setProductID( p.getProductID() );
        setName( p.getName() );
        setInStock( p.getInStock() );
        setPrice( p.getPrice() );
        setMax( p.getMax() );
        setMin( p.getMin() );
        associatedParts = new ArrayList( p.getAssociatedParts() );
    }
    
    public final void setName( String name ) { this.name = name; }
    
    public final String getName() { return this.name; }
    
    public final void setPrice( double price ) { this.price = price; }
    
    public final double getPrice() { return this.price; }
    
    public final void setInStock( int qty ) { this.inStock = qty; }
    
    public final int getInStock( ) { return this.inStock; }
    
    public final void setMin( int min ) throws IllegalInventoryException
    {
        this.min = min; 
    }
    
    public final int getMin() 
    { 
        return this.min; 
    }
    
    public final void setMax( int max ) 
    { 
        this.max = max; 
    }
    
    public final int getMax() 
    { 
        return this.max; 
    }
    
    public void addAssociatedPart( Part part )
    {
        this.associatedParts.add(part);
    }
    
    public List<Part> getAssociatedParts()
    {
        return associatedParts;
    }
    
    /**
     * Attempts to remove part with the given partID
     * from the associatedParts class member.
     * Returns true if the part was found and removed,
     * otherwise false.
     * 
     * @param partID
     * @return 
     */
    public boolean removeAssociatedPart( int partID ) 
    {
        boolean foundPartID = false;
        
        // Scan entire ArrayList, remove ALL
        // occurences of given partID (in case multiple parts of the same
        // ID (e.g., washers) are added to a product
        int index = 0;
        for(int i=0; i < associatedParts.size(); i++)
        {
            // If the part ID matches
            if( associatedParts.get(i).getPartID() == partID )
            {
                index = i;
                foundPartID = true;
                break;
            }
        }
        
        // Done separately, to avoid ConcurrentModificationException
        if( foundPartID )
            associatedParts.remove(index);
        
        return foundPartID;
    }
    
    /**
     * Searches the associatedParts member for a part with the
     * given partID; if one is found, that Part object is returned;
     * if not, null is returned.
     * 
     * @param partID
     * @return 
     */
    public Part lookupAssociatedPart( int partID ) 
    {
        for( Part p : this.associatedParts )
        {
            if( p.getPartID() == partID )
                return p;
        }
        
        return null;
    }
    
    public final void setProductID( int id ) 
    { 
        this.productID = id; 
    }
    
    public final int getProductID() 
    { 
        return this.productID; 
    }
    
    /**
     * Finds the total cost of the parts comprising
     * this Product.
     * @return double Total price
     */
    public double getTotalPartPrice()
    {
        double totPrice = 0.0;
        totPrice 
                = associatedParts
                        .stream()
                        .map((p) -> p.getPrice())
                        .reduce(
                                totPrice, (accumulator, _item) -> 
                                        accumulator + _item
                        );
        
        return totPrice;
    }
    
    /**
     * Validates the Product according to the given criteria, and
     * throws RuntimeExceptions if it finds any errors.
     * 
     * @throws IllegalInventoryException
     * @throws IllegalNameException 
     */
    public final void validate() throws IllegalInventoryException, 
            IllegalNameException, IllegalPriceException
    {
        if( min > max || inStock > max || min > inStock )
            throw new IllegalInventoryException();
        if( productID == -1 || name.equals("") )
            throw new IllegalNameException();
        if( !( associatedParts.size() > 0 ) )
            throw new IllegalPartsException();
        if( getTotalPartPrice() > price )
            throw new IllegalPriceException();
    }
}
