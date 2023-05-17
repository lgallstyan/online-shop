package grid.onlineshop.sweetland.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddProductToCartDto {

    @Min(value = 1, message = "cartId cannot be less than 1")
    private Long cartId;

    @Min(value = 1, message = "productId cannot be less than 1")
    private Long productId;

    @Min(value = 1, message = "quantity cannot be less than 1")
    private Integer quantity;

}
