package com.trollmarket.RestController;

import com.trollmarket.entity.Cart;
import com.trollmarket.exceptionhandler.InsufficientFundsException;
import com.trollmarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController

@RequestMapping("/api/myCart")
public class MyCartRestController {

    @Autowired
    CartService cartService;

    @GetMapping("/index")
    public ResponseEntity<Page<Cart>> myCart(@RequestParam(defaultValue = "1") Integer page, Model model, Authentication authentication){

        Page<Cart> theUserCart = cartService.findAllCartPageable(page, authentication.getName());

        return new ResponseEntity<>(theUserCart, HttpStatus.FOUND);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id){

        String productNameToBeDeleted = cartService.findById(id).getProduct().getName();

        cartService.deleteById(id);
        return new ResponseEntity<>("Order of "
                + productNameToBeDeleted
                + " has been successfully deleted!"
                , HttpStatus.ACCEPTED);
    }

    @GetMapping("/purchase")
    public ResponseEntity<String> purchaseAll(Authentication authentication){

            cartService.purchaseAll(authentication.getName());

            return new ResponseEntity<>("All items successfully bought!", HttpStatus.ACCEPTED);
    }

}
