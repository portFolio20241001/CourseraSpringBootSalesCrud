package com.project.code.Service;

import org.springframework.stereotype.Service;
import com.project.code.Entity.Inventory;
import com.project.code.Entity.Product;
import com.project.code.Repository.InventoryRepository;
import com.project.code.Repository.ProductRepository;

@Service
public class ServiceClass {
	
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    
    public ServiceClass(InventoryRepository inventoryRepository,ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository=productRepository;
    }
    
    public boolean validateInventory(Inventory inventory)
    {
        Inventory result=inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
        
        if(result!=null)
        {
            return false;
        }
        
        return true;
        
    }
    
    public boolean validateProduct(Product product)
    {
        Product result=productRepository.findByName(product.getName());
        
        if(result!=null)
        {
            return false;
        }
        
        return true;
        
    }
    
    public boolean ValidateProductId(long id)
    {
        Product result=productRepository.findByid(id);
        
        System.out.println(result);
        
        if(result==null)
        {
            return false;
        }
        
        return true;
        
    }
    
    public Inventory getInventoryId(Inventory inventory)
    {
        Inventory result=inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
        
        return result;
        
    }
    
}