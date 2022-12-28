package com.trollmarket.RestController;

import com.trollmarket.dto.myCart.CartDTO;
import com.trollmarket.dto.myCart.CartDtoNoBuyerId;
import com.trollmarket.entity.Product;
import com.trollmarket.service.CartService;
import com.trollmarket.service.ProductService;
import com.trollmarket.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {

    @Autowired
    ProductService productService;

    @Autowired
    ShipmentService shipmentService;

    @Autowired
    CartService cartService;

    @GetMapping
    public ResponseEntity<Page<Product>> shop(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "cat", required = false) String cat,
                               @RequestParam(value= "desc", required = false) String desc){

        name = name == null ? "" : name;
        cat = cat == null ? "" : cat;
        desc = desc == null ? "" : desc;

        Page<Product> products = productService.findAllProductContinue(page, name, cat, desc);

        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartDtoNoBuyerId cart
            , Authentication authentication){

        String productRecentlyAdded = productService.findById(cart.getProductID()).getName();

        cartService.saveCart(cart, authentication.getName(), cart.getProductID());
        return new ResponseEntity<>("Successfully added product " + productRecentlyAdded, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/detailProduct")
//    @ResponseBody
//    public Product detailProduct(@RequestParam Long id){
//        return productService.findById(id);
//    }
}