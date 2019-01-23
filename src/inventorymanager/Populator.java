/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

import Model.Outsourced;
import Model.Product;
import Model.Inhouse;
import Model.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to automatically generate collections of randomly populated
 * Part and Product objects.
 * 
 * @author tomas
 */
public class Populator 
{
    private final static int NUM_RECORDS = 20;
    
    public static List<Part> populatePartsTable()
    {
        List<Part> parts = new ArrayList();
        Random r = new Random();
        for(int i=0; i <= NUM_RECORDS; i++)
        {
            int inventory = r.nextInt(500);
            double price = Math.round((10 + 100 * r.nextDouble()) * 100 ) / 100;
            if( i % 2 == 0)
                parts.add( new Inhouse(i, "Part " + i, inventory, price, i+10, i ));
            else
                parts.add( new Outsourced(i, "Part " + i, inventory, price, i+10, i ));
        }
        return parts;
    }
    
    public static List<Product> populateProductsTable()
    {
        List<Product> products = new ArrayList();
        Random r = new Random();
        for(int i=0; i <= NUM_RECORDS; i++)
        {
            int inventory = r.nextInt(500);
            double price = Math.round((10 + 100 * r.nextDouble()) * 100 ) / 100;
            products.add( new Product(i, "Product "+i, inventory, price, i+10, i));
        }
        
        return products;
    }
}    
