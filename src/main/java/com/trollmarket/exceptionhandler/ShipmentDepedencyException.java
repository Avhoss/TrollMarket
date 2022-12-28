package com.trollmarket.exceptionhandler;

public class ShipmentDepedencyException extends RuntimeException{
    public ShipmentDepedencyException(){}
    public ShipmentDepedencyException(String msg){
        super(msg);
    }
}
