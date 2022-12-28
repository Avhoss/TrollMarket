package com.trollmarket.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SellerID")
    @NotNull
    private Seller seller;

    @Column(name = "Name")
    @NotNull
    private String name;

    @Column(name = "Category")
    @NotNull
    private String category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Discontinue")
    private Boolean discontinue;

    private Boolean isOrder;

    public Product(){}

    public Product(Long id, Seller seller, String name, String category, String description, BigDecimal price, Boolean discontinue, Boolean isOrder) {
        this.id = id;
        this.seller = seller;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discontinue = discontinue;
        this.isOrder = isOrder;
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
            return "Yes";
        }else{
            return "No";
        }
    }

    public String getPriceIDR(){
        Locale indonesia = new Locale("id", "ID");
        String indoFormat = NumberFormat.getCurrencyInstance(indonesia).format(this.price);
        return indoFormat;
    }

    public Boolean getOrder() {
        return isOrder;
    }

    public void setOrder(Boolean order) {
        isOrder = order;
    }
}
