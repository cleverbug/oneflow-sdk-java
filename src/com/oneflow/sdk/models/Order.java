package com.oneflow.sdk.models;

public class Order {
    protected OrderData orderData;
    protected Destination destination;

    public Order() {
        orderData = new OrderData();
        destination = new Destination();
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
