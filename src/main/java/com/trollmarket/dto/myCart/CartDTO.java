package com.trollmarket.dto.myCart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartDTO {

    private Long buyerID;

    private Long productID;

    @Min(value = 1,message = "Quantity Minimum 1")
    @NotNull(message= "Quantity must not be null")
    private Integer quantity;

    @NotNull(message = "shipment must not be null")
    private Long shipmentID;


    public CartDTO(){}

    public CartDTO(Long buyerID, Long productID, Integer quantity, Long shipmentID) {
        this.buyerID = buyerID;
        this.productID = productID;
        this.quantity = quantity;
        this.shipmentID = shipmentID;
    }

    public Long getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(Long buyerID) {
        this.buyerID = buyerID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(Long shipmentID) {
        this.shipmentID = shipmentID;
    }


}
