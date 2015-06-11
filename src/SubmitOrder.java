package com.oneflow.sdk;
import com.oneflow.sdk.models.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SubmitOrder {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        OneflowClient client;

        String endpoint =  System.getenv("ONEFLOW_ENDPOINT");
        String token = System.getenv("ONEFLOW_TOKEN");
        String secret = System.getenv("ONEFLOW_SECRET");
        if (args.length == 3) {
            endpoint = args[0];
            token = args[1];
            secret = args[2];
        }

        client = new OneflowClient(endpoint, token, secret);

        Order order = client.createOrder("oneflow");
        Random random = new Random();
        Integer orderId = random.nextInt(1000000);
        order.getOrderData().setSourceOrderId(orderId.toString());
        Item item = new Item();
        item.setSku("GreetingsCard");
        item.setQuantity(2);
        item.setSourceItemId(order.getOrderData().getSourceOrderId() + "-1");

        Component component = new Component();
        component.setCode("ITEM");
        component.setFetch(true);
        component.setPath("https://s3-eu-west-1.amazonaws.com/oneflow-public/business_cards.pdf");
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
        String json = EntityUtils.toString(response.getEntity());
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if (jsonObject.get("error") != null) {
            JsonObject error = jsonObject.get("error").getAsJsonObject();
            System.out.println("Error");
            System.out.println("=====");
            System.out.println(error.get("message").getAsString());
            if (error.get("code").getAsInt() == 208) {
                JsonArray validations = error.get("validations").getAsJsonArray();
                for (final JsonElement validation : validations) {
                    JsonObject v = validation.getAsJsonObject();
                    System.out.println(v.get("path").getAsString() + " -> " + v.get("message").getAsString());
                }
            }
        }
        else {
            System.out.println("Success");
            System.out.println("=======");
            JsonObject savedOrder = jsonObject.get("order").getAsJsonObject();

            System.out.println("Order ID      : " + savedOrder.get("_id").getAsString());
        }
    }
}
