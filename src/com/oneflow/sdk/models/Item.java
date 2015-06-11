package com.oneflow.sdk.models;

import java.util.LinkedList;
import java.util.List;

public class Item {
    protected List<Object> attributes;
    protected List<Component> components;
    protected long quantity;
    protected long shipmentIndex;
    protected long printQuantity;
    protected String sourceItemId;
    protected String barcode;
    protected String sku;

    public Item() {
        components = new LinkedList<Component>();
        quantity = 1;
        shipmentIndex = 0;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getShipmentIndex() {
        return shipmentIndex;
    }

    public void setShipmentIndex(long shipmentIndex) {
        this.shipmentIndex = shipmentIndex;
    }

    public String getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(String sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
