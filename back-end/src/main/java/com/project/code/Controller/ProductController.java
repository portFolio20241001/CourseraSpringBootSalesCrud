package com.project.code.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.code.Entity.Product;
import com.project.code.Repository.InventoryRepository;
import com.project.code.Repository.OrderItemRepository;
import com.project.code.Repository.ProductRepository;
import com.project.code.Service.ServiceClass;

@RequestMapping("/product")
@RestController
public class ProductController {
	
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ServiceClass serviceClass;
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product) {
    	
        Map<String, String> map = new HashMap<>();
        
        if (!serviceClass.validateProduct(product)) {
        	
            map.put("message", "商品はすでにデータベースに存在します");
            return map;
            
        }
        
        try {
        	
            productRepository.save(product);
            map.put("message", "商品が正常に追加されました");
            
        }
        catch (DataIntegrityViolationException e) {
        	
            map.put("message", "SKUは一意である必要があります");
            
        }
        
        return map;
        
    }
    
    @GetMapping("/product/{id}")
    public Map<String, Object> getProductbyId(@PathVariable Long id) {
    	
        System.out.println("結果: ");
        System.out.println("結果: ");
        System.out.println("結果: ");
        
        Map<String, Object> map = new HashMap<>();
        
        Product result = productRepository.findByid(id);
        System.out.println("結果: "+result);
        
        map.put("products", result);
        return map;
        
    }
    
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {
    	
        Map<String, String> map = new HashMap<>();
        
        try {
        	
            productRepository.save(product);
            map.put("message", "データが正常に更新されました");
            
        } catch (Error e) {
        	
            map.put("message", "エラーが発生しました");
            
        }
        
        return map;
        
    }
    
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterbyCategoryProduct(@PathVariable String name,@PathVariable String category) {
    	
        Map<String, Object> map = new HashMap<>();
        
        if(name.equals("null"))
        {
            map.put("products", productRepository.findByCategory(category));
            return map;
        }
        else if(category.equals("null"))
        {
            map.put("products", productRepository.findProductBySubName(name));
            return map;
        }
        
        map.put("products",productRepository.findProductBySubNameAndCategory(name,category));
        return map;
        
    }
    
    @GetMapping
    public Map<String, Object> listProduct() {
    	
        Map<String, Object> map = new HashMap<>();
        
        map.put("products",productRepository.findAll());
        return map;
        
    }  
    
    @GetMapping("filter/{category}/{storeid}")
    public Map<String, Object> getProductbyCategoryAndStoreId(@PathVariable String category,@PathVariable long storeid) {
    	
       Map<String, Object> map = new HashMap<>();
       
       List<Product> result = productRepository.findProductByCategory(category,storeid);
       
        map.put("product", result);
        return map;
        
    }
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {
    	
        Map<String, String> map = new HashMap<>();
        
        if (!serviceClass.ValidateProductId(id)) {
        	
            map.put("message", "Id " + id + " はデータベースに存在しません");
            return map;
            
        }
        
        inventoryRepository.deleteByProductId(id);
        orderItemRepository.deleteByProductId(id);
        productRepository.deleteById(id);
        
        map.put("message", "ID: " + id + " の商品が正常に削除されました");
        return map;
        
    }
    
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
    	
        Map<String, Object> map = new HashMap<>();
        
        map.put("products", productRepository.findProductBySubName(name));
        return map;
        
    }
    
}