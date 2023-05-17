package grid.onlineshop.sweetland.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginDto {

    @Email(message = "Email msut be valid.")
    private String email;

    private String password;

}
