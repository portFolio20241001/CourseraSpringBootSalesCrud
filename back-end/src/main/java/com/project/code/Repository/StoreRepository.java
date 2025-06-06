package com.project.code.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.project.code.Entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	
    Store findByid(Long id);
    
    @Query("SELECT p FROM Store p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Store> findBySubName(String pname);
    
}