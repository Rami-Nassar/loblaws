package com.loblaws.processingservices.model.order;

import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SqlResultSetMapping(
        name = "OrderQueryMapping",
        classes = {
                @ConstructorResult(
                        targetClass = OrderSummaryHelper.class,
                        columns = {
                                @ColumnResult(name = "orderId", type = UUID.class),
                                @ColumnResult(name = "cashierId", type = UUID.class),
                                @ColumnResult(name = "productId", type = UUID.class),
                                @ColumnResult(name = "productName", type = String.class),
                                @ColumnResult(name = "UPCNumber", type = Integer.class),
                                @ColumnResult(name = "price", type = Double.class),
                                @ColumnResult(name = "quantity", type = Integer.class)
                        }
                )
        }
)


@NamedNativeQuery(name = "OrderSummaryHelper.getOrderSummary", query = "SELECT orders.orderId, orders.cashierId, product.productId, product.productName, product.UPCNumber, product.price, orderxproduct.quantity FROM orderxproduct INNER JOIN product ON product.productId = orderxproduct.productId INNER JOIN orders ON orders.orderId = orderxproduct.orderId WHERE orderxproduct.orderId = :orderId", resultSetMapping = "OrderQueryMapping")
public class OrderSummaryHelper {
	@Id
    private UUID orderId;
    private UUID cashierId;
    private UUID productId;
    private String productName;
    private Integer UPCNumber;
    private Double price;
    private Integer quantity;
    
}
