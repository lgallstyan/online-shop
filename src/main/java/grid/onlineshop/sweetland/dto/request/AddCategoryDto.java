package grid.onlineshop.sweetland.dto.request;

import grid.onlineshop.sweetland.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AddCategoryDto {

    @NotBlank
    private String name;


}
