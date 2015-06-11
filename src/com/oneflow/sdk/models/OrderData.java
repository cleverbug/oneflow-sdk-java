package com.oneflow.sdk.models;

import java.util.LinkedList;
import java.util.List;

public class OrderData {
    protected List<String> attributes;
    protected List<Item> items;
    protected List<Shipment> shipments;
    protected String sourceOrderId;

    public OrderData() {
        items = new LinkedList<Item>();
        shipments = new LinkedList<Shipment>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public String getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(String sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }
}
