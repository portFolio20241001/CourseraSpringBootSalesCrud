package com.project.code.Entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Store {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "名前はnullにできません")
    @NotBlank(message = "名前は空にできません")
    private String name;
    
    @NotNull(message = "住所はnullにできません")
    @NotBlank(message = "住所は空にできません")
    private String address;
    
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @JsonManagedReference("inventory-store")
    private List<Inventory> inventory;
    
    // ゲッターとセッター
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Inventory> getInventory() {
        return inventory;
    }
    
    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }
    // コンストラクター（必要に応じて）
    public Store() {
    }
    
    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
}
