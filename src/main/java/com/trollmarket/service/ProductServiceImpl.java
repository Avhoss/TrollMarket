package com.trollmarket.service;

import com.trollmarket.dao.CartRepository;
import com.trollmarket.dao.ProductRepository;
import com.trollmarket.dao.SellerRepository;
import com.trollmarket.dto.product.ProductDTO;
import com.trollmarket.entity.Cart;
import com.trollmarket.entity.Product;
import com.trollmarket.entity.Seller;
import com.trollmarket.exceptionhandler.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CartRepository cartRepository;

    private final int rowsInPage = 5;

    @Override
    public void save(ProductDTO dto, String username) {
        Seller seller =  sellerRepository.findByUsername(username);

        if(dto.getDiscontinue() == null) {
            dto.setDiscontinue(Boolean.FALSE);
        }

        if(dto.getId() != null &&
                productRepository.findProductSellerById(seller.getId(), dto.getId()) == 0){
                throw new RuntimeException("Seller " + seller.getFullName() + " tidak memiliki produk dengan id " + dto.getId());
        }

        Product product = new Product(
                dto.getId(),
                seller,
                dto.getName(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getDiscontinue(),
                Boolean.FALSE
        );
//        if(dto.getId()!=null){
//            product.setDiscontinue(productRepository.findById(dto.getId()).get().getDiscontinue());
//        }

        productRepository.save(product);
    }
    

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }


    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product tempProduct = null;
        if(productOptional.isPresent()){
            tempProduct = productOptional.get();
        }
        return tempProduct;
    }

    @Override
    public ProductDTO findProductDTOById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->{throw new RuntimeException("Product Not Found");
                });
        ProductDTO productDTO = new ProductDTO(product.getId(), product.getSeller().getId(),
                product.getName(),product.getCategory(),product.getDescription(),product.getPrice(),
                product.getDiscontinue());
        return productDTO;
    }

    @Override
    public void delete(Long id) {

        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAllProductContinue(int page, String name, String cat, String desc) {
        Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
        return productRepository.findAllProductContinue(pagination, name, cat, desc);
    }

    @Override
    public Boolean isOrder(Long id) {
        Long totalOrder = productRepository.countOrderProduct(id);

        return totalOrder > 0;
    }

    @Override
    public void orderedProduct(String username) {
        Page<Product> productsPageable = productRepository.findAllProductBySeller(username,Pageable.unpaged());
        List<Product> products = productsPageable.getContent();

        for(Product pro : products){
            if(productRepository.countOrderProduct(pro.getId()) > 0 ||
                productRepository.countCartProduct(pro.getId()) > 0 ){
                pro.setOrder(true);
            }else{
                pro.setOrder(false);
            }
            productRepository.save(pro);
        }
    }

    @Override
    public Page<Product> findAllProductBySeller(String username,Integer page) {
        Pageable pagination = PageRequest.of(page - 1,rowsInPage,Sort.by("id"));
        return productRepository.findAllProductBySeller(username,pagination);
    }

    @Override
    public Product findProductSellerById(Long id, String username) {
        List<Product> allProductSeller = productRepository.findAllProductBySeller(username,Pageable.unpaged()).getContent();
        Product product = null;
        for(Product prod : allProductSeller){
            if(prod.getId() == id){
                product = prod;
            }
        }

        if(product == null){
            throw new ObjectNotFound("product with id " + id + " not found");
        }

        return product;
    }

    @Override
    public Long CountProductSellerById(Long sellerID, Long productID) {
        return productRepository.findProductSellerById(sellerID, productID);
    }
}
