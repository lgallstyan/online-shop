package grid.onlineshop.sweetland.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdminLoginDto {


    private String email;

    private String password;

}
