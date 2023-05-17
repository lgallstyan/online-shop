package grid.onlineshop.sweetland.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateUserDto {

    @Min(value = 1, message = "customerId cannot be less than 1")
    private Long customerId;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

}
