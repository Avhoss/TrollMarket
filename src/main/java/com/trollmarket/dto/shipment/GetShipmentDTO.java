package com.trollmarket.dto.shipment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GetShipmentDTO {
    private Long id;

    private String name;

    private BigDecimal price;

    private Boolean service;

    private Long totalTransaction;

    public GetShipmentDTO(){}

    public GetShipmentDTO(Long id, String name, BigDecimal price, Boolean service, Long totalTransaction) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.service = service;
        this.totalTransaction = totalTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getService() {
        return service;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    public Long getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Long totalTransaction) {
        this.totalTransaction = totalTransaction;
    }
    public String getStatus(){
        if(this.service == true){
            return "Yes";
        }else{
            return "No";
        }
    }
}
