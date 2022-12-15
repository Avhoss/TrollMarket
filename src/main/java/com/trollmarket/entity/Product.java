package com.trollmarket.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SellerID")
    private Seller seller;

    @Column(name = "Name")
    private String name;

    @Column(name = "Category")
    private String category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Discontinue")
    private Boolean discontinue;

    public Product(){}

    public Product(Long id, Seller seller, String name, String category, String description, BigDecimal price, Boolean discontinue) {
        this.id = id;
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discontinue = discontinue;
    }

    public Product(Long id, String name, String category, String description, BigDecimal price, Boolean discontinue) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discontinue = discontinue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getDiscontinue() {
        return discontinue;
    }

    public void setDiscontinue(Boolean discontinue) {
        this.discontinue = discontinue;
    }

    public String getStatus(){
        if(this.discontinue == true){
            return "yes";
        }else{
            return "No";
        }
    }
}
