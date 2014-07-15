/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.rest.controller;

import com.yummynoodlebar.core.events.orders.OrderDetails;
import com.yummynoodlebar.core.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.core.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.core.events.orders.RequestOrderDetailsEvent;
import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.rest.dto.OrderDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author alexp
 */
@Controller
@RequestMapping("/aggregators/orders")
public class OrderQueriesController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderQueriesController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        for (OrderDetails detail : orderService.requestAllOrders(new RequestAllOrdersEvent()).getOrdersDetails()) {
            orders.add(OrderDTO.fromOrderDetails(detail));
        }
        return orders;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable String id) {

        OrderDetailsEvent details = orderService.requestOrderDetails(new RequestOrderDetailsEvent(UUID.fromString(id)));

        if (!details.isEntityFound()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OrderDTO order = OrderDTO.fromOrderDetails(details.getOrderDetails());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
