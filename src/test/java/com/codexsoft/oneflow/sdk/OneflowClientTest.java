package com.codexsoft.oneflow.sdk;

import com.codexsoft.oneflow.sdk.models.*;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.sql.Timestamp;
import java.util.Calendar;

public class OneflowClientTest extends TestCase {
    OneflowClient client;

    public void setUp() throws Exception {
        super.setUp();
        String endpoint = "http://dev.oneflowcloud.com/api";
        String token = "2131386507703";
        String secret = "1cee9f300142df9f5c4817c3cd200214769eec9cbdec4f80";
        client = new OneflowClient(endpoint, token, secret);
    }

    public void testCreateOrder() throws Exception {
        Order order = client.createOrder("ultraprint");
        order.getOrderData().setSourceOrderId("1231321");
        Item item = new Item();
        item.setSku("TN Greetings Card EC");
        item.setQuantity(2);
        item.setSourceItemId(order.getOrderData().getSourceOrderId() + "-1");

        Component component = new Component();
        component.setCode("ITEM");
        component.setFetch(false);
        component.setPath("greetings-card.pdf");
        item.getComponents().add(component);
        order.getOrderData().getItems().add(item);

        Shipment shipment = new Shipment();
        shipment.setCarrier(new Carrier("royalmail", "firstclass"));
        Address address = new Address();
        address.setName("Nigel Watson");
        address.setAddress1("999 Letsbe Avenue");
        address.setTown("London");
        address.setIsoCountry("UK");
        address.setPostcode("EC2N 0BJ");
        shipment.setShipTo(address);
        order.getOrderData().getShipments().add(shipment);

        HttpResponse response = client.submitOrder();
        System.out.println(response);
        System.out.println();
        System.out.println(response.getEntity().getContentType());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    public void testMakeToken() throws Exception {
        String auth = client.makeToken("POST", "/api/order", "Mon Nov 03 2014 12:12:41 GMT+0300 (MSK)");
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        assertEquals("2131386507703:d5f3f4d2b44aed6b0cd538ad089acd5ab1ba9c2b", auth);
    }

    public void testValidateOrder() throws Exception {
        System.out.println(client.validateOrder());
    }
}