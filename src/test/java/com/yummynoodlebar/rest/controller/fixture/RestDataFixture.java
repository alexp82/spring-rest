/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.rest.controller.fixture;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.core.domain.fixtures.OrdersFixtures;
import static com.yummynoodlebar.core.domain.fixtures.OrdersFixtures.YUMMY_ITEM;
import com.yummynoodlebar.core.events.orders.AllOrdersEvent;
import com.yummynoodlebar.core.events.orders.OrderDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author alexp
 */
public class RestDataFixture {

    public static AllOrdersEvent allOrders() {
        List<OrderDetails> orders = new ArrayList<>();

        orders.add(standardOrderDetails());
        orders.add(standardOrderDetails());
        orders.add(standardOrderDetails());

        return new AllOrdersEvent(orders);
    }

    public static Order standardOrder() {
        Order order = new Order(new Date());

        order.setOrderItems(Collections.singletonMap(YUMMY_ITEM, 12));

        return order;
    }

    public static OrderDetails customKeyOrderDetails(UUID key) {
        OrderDetails orderdetails = new OrderDetails(key);

        orderdetails.setOrderItems(Collections.singletonMap(YUMMY_ITEM, 12));

        return orderdetails;
    }

    public static OrderDetails standardOrderDetails() {
        return customKeyOrderDetails(UUID.randomUUID());
    }

    public static String standardOrderJSON() {
        return "{ \"items\": { \"" + OrdersFixtures.YUMMY_ITEM + "\": 12, \"yummy15\": 42 } }";
    }
}
