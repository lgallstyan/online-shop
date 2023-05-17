package grid.onlineshop.sweetland.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@RequiredArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String orderStatus;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL) // Unidirectional
    @JoinColumn(name = "ORDERID")
    private List<CartItem> cartProductList;

    @JsonIgnore
    @ManyToOne // Bidirectional
    private Address address;

}