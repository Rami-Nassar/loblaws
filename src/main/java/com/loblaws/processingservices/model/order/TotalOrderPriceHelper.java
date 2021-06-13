package com.loblaws.processingservices.model.order;

import java.util.UUID;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

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
        name = "OrderTotalQueryMapping",
        classes = {
                @ConstructorResult(
                        targetClass = TotalOrderPriceHelper.class,
                        columns = {
                                @ColumnResult(name = "orderId", type = UUID.class),                                
                                @ColumnResult(name = "Total", type = Double.class)
                        }
                )
        }
)


@NamedNativeQuery(name = "TotalOrderPriceHelper.getOrderTotal", query = "SELECT orders.orderId, SUM(price * quantity) AS Total FROM orderxproduct INNER JOIN product ON product.productId = orderxproduct.productId INNER JOIN orders ON orders.orderId = orderxproduct.orderId WHERE orderxproduct.orderId = :orderId", resultSetMapping = "OrderTotalQueryMapping")
public class TotalOrderPriceHelper {
	@Id
    private UUID orderId;
	private Double Total;
}
