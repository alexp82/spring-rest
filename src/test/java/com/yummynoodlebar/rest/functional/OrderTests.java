/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yummynoodlebar.rest.functional;

import com.yummynoodlebar.rest.controller.fixture.RestDataFixture;
import com.yummynoodlebar.rest.dto.OrderDTO;
import java.util.Arrays;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author alexp
 */
public class OrderTests {

    @Test
    public void thatOrdersCanBeAddedAndQueried() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate template = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<>(
                RestDataFixture.standardOrderJSON(), headers);

        ResponseEntity<OrderDTO> entity = template.postForEntity(
                "http://localhost:8080/aggregators/orders",
                requestEntity, OrderDTO.class);

        String path = entity.getHeaders().getLocation().getPath();

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertTrue(path.startsWith("/aggregators/orders/"));
        OrderDTO order = entity.getBody();

        System.out.println("The Order ID is " + order.getKey());
        System.out.println("The Location is " + entity.getHeaders().getLocation());

        assertEquals(2, order.getItems().size());
    }
}
