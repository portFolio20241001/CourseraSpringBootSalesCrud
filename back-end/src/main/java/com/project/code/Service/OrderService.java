package com.project.code.Service;

import com.project.code.Entity.Customer;
import com.project.code.Entity.Inventory;
import com.project.code.Entity.OrderDetails;
import com.project.code.Entity.OrderItem;
import com.project.code.Entity.PlaceOrderRequestDTO;
import com.project.code.Entity.PurchaseProductDTO;
import com.project.code.Entity.Store;
import com.project.code.Repository.CustomerRepository;
import com.project.code.Repository.InventoryRepository;
import com.project.code.Repository.OrderDetailsRepository;
import com.project.code.Repository.OrderItemRepository;
import com.project.code.Repository.ProductRepository;
import com.project.code.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {
	
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
    	
        // 1. 顧客を取得または作成する
        Customer existingCustomer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        
        Customer customer = new Customer();
        
        customer.setName(placeOrderRequest.getCustomerName());
        customer.setEmail(placeOrderRequest.getCustomerEmail());
        customer.setPhone(placeOrderRequest.getCustomerPhone());
        
        if (existingCustomer == null) {
            customer = customerRepository.save(customer);
        }
        else{
            customer=existingCustomer;
        }
        
        // 2. 店舗を取得する
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("店舗が見つかりません"));
        
        // 3. 注文詳細を作成する
        OrderDetails orderDetails = new OrderDetails();
        
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderDetails.setDate(java.time.LocalDateTime.now()); // 現在の日時を使用
        
        orderDetails = orderDetailsRepository.save(orderDetails); 
        
        // 4. 注文アイテム（購入した商品）を作成して保存する
        List<PurchaseProductDTO> purchaseProducts = placeOrderRequest.getPurchaseProduct();
        
        for (PurchaseProductDTO productDTO : purchaseProducts) {
        	
            OrderItem orderItem = new OrderItem();
            
            Inventory inventory=inventoryRepository.findByProductIdandStoreId(productDTO.getId(),placeOrderRequest.getStoreId());
            
            inventory.setStockLevel(inventory.getStockLevel()-productDTO.getQuantity());
            
            inventoryRepository.save(inventory);
            
            orderItem.setOrder(orderDetails); // 注文アイテムに注文をリンクする
            orderItem.setProduct(productRepository.findByid(productDTO.getId()));
            orderItem.setQuantity(productDTO.getQuantity());
            orderItem.setPrice(productDTO.getPrice()*productDTO.getQuantity());
            
            orderItemRepository.save(orderItem); // 注文アイテムを保存
            
        }
    }
}