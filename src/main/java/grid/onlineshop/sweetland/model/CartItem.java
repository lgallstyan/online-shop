package grid.onlineshop.sweetland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grid.onlineshop.sweetland.util.enums.CartItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull(message = "slot count cannot be null.")
//    @Min(value = 1, message = "quantity cannot be less than 1")
    private Integer quantity;

    // @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CartItemStatus cmpStatus;

    @JsonIgnore
    @ManyToOne // Bidirectional
    private Product product;

    @JsonIgnore
    @ManyToOne // Bidirectional
    private Cart cart;

//    @Transient
//    private Integer totalPrice;

}
