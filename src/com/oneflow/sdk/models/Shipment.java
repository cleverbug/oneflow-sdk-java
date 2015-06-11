package com.oneflow.sdk.models;

import java.util.LinkedList;
import java.util.List;

public class Shipment {
    protected int shipmentIndex;
    protected List<Object> packages;
    protected Address shipTo;
    protected Address returnTo;
    protected Carrier carrier;

    public Shipment() {
        shipmentIndex = 0;
        packages = new LinkedList<Object>();
    }

    public int getShipmentIndex() {
        return shipmentIndex;
    }

    public void setShipmentIndex(int shipmentIndex) {
        this.shipmentIndex = shipmentIndex;
    }

    public List<Object> getPackages() {
        return packages;
    }

    public void setPackages(List<Object> packages) {
        this.packages = packages;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Address getReturnTo() {
        return returnTo;
    }

    public void setReturnTo(Address returnTo) {
        this.returnTo = returnTo;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
}
