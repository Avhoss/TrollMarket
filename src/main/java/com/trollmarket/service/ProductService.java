package com.trollmarket.service;

import com.trollmarket.dto.product.ProductDTO;
import com.trollmarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {

    void save(ProductDTO product, String username);

    List<Product> findAllProduct();

    Product findById(Long id);

    ProductDTO findProductDTOById(Long id);

    void delete(Long id);

    Page<Product> findAllProductContinue(int page, String name, String cat, String desc);

    Boolean isOrder(Long id);

    void orderedProduct(String username);
    Page<Product> findAllProductBySeller(String username,Integer page);

    Product findProductSellerById(Long id, String username);

    Long CountProductSellerById(Long sellerID, Long productID);
}
