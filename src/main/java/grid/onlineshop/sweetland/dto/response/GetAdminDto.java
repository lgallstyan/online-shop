package grid.onlineshop.sweetland.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GetAdminDto {

    private String name;

    private String email;

    private String phone;

}
