package grid.onlineshop.sweetland.dto.request;

import grid.onlineshop.sweetland.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Integer price;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String manufacturer;

    private String category;

    public static ProductDto fromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());

        productDto.setImageUrl(product.getImageUrl());

        productDto.setManufacturer(product.getManufacturer());
        productDto.setName(product.getName());

        return productDto;
    }


}
