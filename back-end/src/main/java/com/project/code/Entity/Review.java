package com.project.code.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;

@Document(collection = "reviews")
public class Review {
	
    @Id
    private String id; // MongoDBはIDフィールドにStringを使用します
    
    @NotNull(message = "顧客はnullにできません")
    private Long customerId; // レビューを作成した顧客
    
    @NotNull(message = "製品はnullにできません")
    private Long productId; // レビューされる製品
    
    @NotNull(message = "店舗はnullにできません")
    private Long storeId; // 製品に関連する店舗
    
    @NotNull(message = "評価はnullにできません")
    private Integer rating; // 5段階評価
    
    private String comment; // 製品に関する任意のコメント
    
    // コンストラクター
    public Review() {
    }
    
    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }
    
    // ゲッターとセッター
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Long getStoreId() {
        return storeId;
    }
    
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
