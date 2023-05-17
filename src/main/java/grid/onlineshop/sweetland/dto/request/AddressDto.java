package grid.onlineshop.sweetland.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressDto {

    @NotBlank
    private String addressLine;

    @NotBlank
    private String country;

    @NotBlank
    private String province;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;


}
