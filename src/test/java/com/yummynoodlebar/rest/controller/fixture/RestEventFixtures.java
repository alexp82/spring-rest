/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.rest.controller.fixture;

import static com.yummynoodlebar.core.domain.fixtures.OrdersFixtures.standardOrderDetails;
import com.yummynoodlebar.core.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.core.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.core.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.core.events.orders.OrderStatusDetails;
import com.yummynoodlebar.core.events.orders.OrderStatusEvent;
import static com.yummynoodlebar.rest.controller.fixture.RestDataFixture.customKeyOrderDetails;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author alexp
 */
public class RestEventFixtures {

    public static OrderStatusEvent orderStatusNotFound(UUID key) {
        return OrderStatusEvent.notFound(key);
    }

    public static OrderStatusEvent orderStatus(UUID key, String status) {
        return new OrderStatusEvent(key, new OrderStatusDetails(new Date(), status));
    }

    public static OrderDetailsEvent orderDetailsNotFound(UUID key) {
        return OrderDetailsEvent.notFound(key);
    }

    public static OrderDetailsEvent orderDetailsEvent(UUID key) {
        return new OrderDetailsEvent(key, customKeyOrderDetails(key));
    }

    public static OrderCreatedEvent orderCreated(UUID key) {
        return new OrderCreatedEvent(key, customKeyOrderDetails(key));
    }

    public static OrderDeletedEvent orderDeleted(UUID key) {
        return new OrderDeletedEvent(key, standardOrderDetails());
    }

    public static OrderDeletedEvent orderDeletedFailed(UUID key) {
        return OrderDeletedEvent.deletionForbidden(key, standardOrderDetails());
    }

    public static OrderDeletedEvent orderDeletedNotFound(UUID key) {
        return OrderDeletedEvent.notFound(key);
    }
}
