package com.trollmarket.dao;

import com.trollmarket.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

   @Query(
           """
           SELECT c
           FROM Cart AS c 
           JOIN c.buyer AS b
           WHERE b.account.username = :username        
           """
   )
   Page<Cart> findAllCartBuyer(Pageable pageable, @Param("username") String username);

   @Query(
           """
           SELECT c
           FROM Cart AS c 
           JOIN c.buyer AS b
           WHERE b.account.username = :username        
           """
   )
   List<Cart> findCartsBuyer(@Param("username") String username);

   @Query("""
           SELECT c
           FROM Cart as c
           WHERE c.product.id = :id
           """)
   List<Cart> findCartByProduct(@Param("id") Long id);

   @Query("""
           SELECT c
           FROM Cart as c
           WHERE c.shipment.id = :id
           """)
   List<Cart> findCartByShipment(@Param("id") Long id);
}
