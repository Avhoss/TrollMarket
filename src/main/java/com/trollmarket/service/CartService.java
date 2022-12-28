package com.trollmarket.service;

import com.trollmarket.dto.myCart.CartDTO;
import com.trollmarket.dto.myCart.CartDtoNoBuyerId;
import com.trollmarket.entity.Cart;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    Cart findById(Long id);
    void saveCart(CartDTO cartDTO, String usernameBuyer, Long ProductID);

    void saveCart(CartDtoNoBuyerId cartDTO, String usernameBuyer, Long ProductID);


    List<Cart> findAllCart();

    Page<Cart> findAllCartPageable(int page, String username);

    void deleteById(Long id);

    void purchaseAll(String name);
}
