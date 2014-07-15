/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.rest.controller;

import static com.yummynoodlebar.core.domain.fixtures.OrdersFixtures.YUMMY_ITEM;
import com.yummynoodlebar.core.events.orders.CreateOrderEvent;
import com.yummynoodlebar.core.services.OrderService;
import static com.yummynoodlebar.rest.controller.fixture.RestDataFixture.standardOrderJSON;
import static com.yummynoodlebar.rest.controller.fixture.RestEventFixtures.orderCreated;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *
 * @author alexp
 */
public class CreateNewOrderIntegrationTest {

    MockMvc mockMvc;

    @InjectMocks
    OrderCommandsController controller;

    @Mock
    OrderService orderService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

        when(orderService.createOrder(any(CreateOrderEvent.class))).thenReturn(
                orderCreated(UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13")));
    }

    //createOrder - validation?
    @Test
    public void thatCreateOrderUsesHttpCreated() throws Exception {

        this.mockMvc.perform(
                post("/aggregators/orders")
                .content(standardOrderJSON())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void thatCreateOrderRendersAsJson() throws Exception {

        this.mockMvc.perform(
                post("/aggregators/orders")
                .content(standardOrderJSON())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items['" + YUMMY_ITEM + "']").value(12))
                .andExpect(jsonPath("$.key").value("f3512d26-72f6-4290-9265-63ad69eccc13"));
    }

    @Test
    public void thatCreateOrderPassesLocationHeader() throws Exception {

        this.mockMvc.perform(
                post("/aggregators/orders")
                .content(standardOrderJSON())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", Matchers.endsWith("/aggregators/orders/f3512d26-72f6-4290-9265-63ad69eccc13")));
    }
}
