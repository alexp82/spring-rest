/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.config;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.core.repository.OrdersMemoryRepository;
import com.yummynoodlebar.core.repository.OrdersRepository;
import com.yummynoodlebar.core.services.OrderEventHandler;
import com.yummynoodlebar.core.services.OrderService;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author alexp
 */
@Configuration
public class CoreConfig {

    @Bean
    public OrderService createService(OrdersRepository repo) {
        return new OrderEventHandler(repo);
    }

    @Bean
    public OrdersRepository createRepo() {
        return new OrdersMemoryRepository(new HashMap<UUID, Order>());
    }   
}
