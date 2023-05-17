package grid.onlineshop.sweetland.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactForm {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String message;



}
