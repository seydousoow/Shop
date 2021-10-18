package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sid.config.auditing.AuditMetadata;
import com.sid.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document("orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AuditMetadata {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String idOrder;
    @Indexed(unique = true)
    private String code;
    private LocalDateTime date;
    private List<CartItemDto> listItem;
    private String codeClient;
    private String orderPlacedBy;
    private double orderPrice;
    private double receivedAmount;
    private double remainingAmount;
    private String orderStatus;
}

