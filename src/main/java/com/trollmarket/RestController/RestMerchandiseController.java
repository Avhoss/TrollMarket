package com.trollmarket.RestController;

import com.trollmarket.dto.product.ProductDTO;
import com.trollmarket.entity.Product;
import com.trollmarket.exceptionhandler.ObjectNotFound;
import com.trollmarket.service.ProductService;
import com.trollmarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/merchandise")
public class RestMerchandiseController {

    @Autowired
    ProductService productService;

    @Autowired
    SellerService sellerService;

    @GetMapping()
    public ResponseEntity<Page<Product>> getAllMerchandise(@RequestParam(defaultValue = "1") Integer page,
                                                           Authentication authentication, Model mode) {
        return new ResponseEntity<>(productService.findAllProductBySeller(authentication.getName(), page), HttpStatus.FOUND);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProductMerchandise(@Valid @RequestBody ProductDTO productDTO,
                                                        Authentication authentication) {
        productService.save(productDTO, authentication.getName());
        return new ResponseEntity<>("success add product!", HttpStatus.OK);
    }

    @GetMapping("/detailProduct/{id}")
    public ResponseEntity<Product> getDetailProduct(@PathVariable("id") Long id, Authentication authentication) {
        Product product = productService.findProductSellerById(id, authentication.getName());

        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }

    @PutMapping("/editProduct/{id}")
    public ResponseEntity<String> editProductById(@PathVariable("id") Long id,
                                                  @Valid @RequestBody ProductDTO productDTO,
                                                  Authentication authentication) {

        productDTO.setId(id);
        productService.save(productDTO, authentication.getName());
        return new ResponseEntity<>("success edited product!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id,
                                                    Authentication authentication){
        if(productService.CountProductSellerById(sellerService.findSellerByUsername(authentication.getName()).getId(), id) == 0){
            throw new ObjectNotFound("seller " + authentication.getName() + "tidak memiliki produk dengan id "+id);
        }

        productService.delete(id);
        return new ResponseEntity<>("success deleted product!", HttpStatus.OK);
    }


}
