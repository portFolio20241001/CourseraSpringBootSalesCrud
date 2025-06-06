package com.project.code.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.code.Entity.Customer;
import com.project.code.Entity.Review;
import com.project.code.Repository.CustomerRepository;
import com.project.code.Repository.ReviewRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;
    
    @GetMapping("/{storeId}/{productId}")
    public Map<String,Object> getReviews(@PathVariable long storeId, @PathVariable long productId)
    {
        Map<String, Object> map = new HashMap<>();
        
         List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId,productId);
         
         List<Map<String, Object>> reviewsWithCustomerNames = new ArrayList<>();
         
         // 各レビューについて、顧客の詳細を取得し、レスポンスに追加
         for (Review review : reviews) {
        	 
             Map<String, Object> reviewMap = new HashMap<>();
             
             reviewMap.put("review", review.getComment());
             reviewMap.put("rating", review.getRating());
             
             // customerIdを使用して顧客の詳細を取得
             Customer customer = customerRepository.findByid(review.getCustomerId());
             
             if (customer != null) {
            	 
                 reviewMap.put("customerName", customer.getName());  
                 
             } else {
            	 
                 reviewMap.put("customerName", "不明");
                 
             }
             
             reviewsWithCustomerNames.add(reviewMap);
             
         }
         
         map.put("reviews", reviewsWithCustomerNames);
         return map;
         
    }
    
    @GetMapping
    public Map<String,Object> getAllReviews()
    {
    	
        Map<String,Object> map=new HashMap<>();
        
        map.put("reviews",reviewRepository.findAll());
        return map;
        
    }
    
}
