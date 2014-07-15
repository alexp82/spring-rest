package com.yummynoodlebar.rest.dto;

import com.yummynoodlebar.core.events.orders.OrderStatusDetails;
import java.util.Date;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orderstatus")
public class OrderStatusDTO {

  private UUID orderId;

  private Date statusDate;

  private String status;

  public static OrderStatusDTO fromOrderStatusDetails(UUID key, OrderStatusDetails orderDetails) {
    OrderStatusDTO status = new OrderStatusDTO();

    status.orderId = key;
    status.status = orderDetails.getStatus();
    status.statusDate = orderDetails.getStatusDate();

    return status;
  }

  public UUID getOrderId() {
    return orderId;
  }

  public Date getStatusDate() {
    return statusDate;
  }

  public String getStatus() {
    return status;
  }
}
