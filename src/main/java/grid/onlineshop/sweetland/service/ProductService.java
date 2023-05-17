package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.ProductDto;
import grid.onlineshop.sweetland.exceptions.BadRequestException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryNotFoundException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.model.Category;
import grid.onlineshop.sweetland.model.Product;
import grid.onlineshop.sweetland.repository.CategoryRepository;
import grid.onlineshop.sweetland.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    public List<Product> viewAllProducts() throws ProductNotFoundException {

        List<Product> allProducts = productRepository.findAll();

        if (allProducts.size() == 0) {
            throw new ProductNotFoundException("Product does not exists...");
        } else {
            return allProducts;
        }

    }

    public List<Product> searchProducts(String query) {
        List<Product> searchResults = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            if (product.getName().toLowerCase().startsWith(query.toLowerCase())) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(ProductDto productDto) throws Exception {

        Product existingProduct = productRepository.
                findByManufacturerAndAndName(productDto.getManufacturer(), productDto.getName());

        Product product = new Product();

        if (existingProduct != null) {
            if (existingProduct.getManufacturer().equals(productDto.getManufacturer())) {
                throw new ProductAlreadyExistsException("Product already exists...");
            }
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setManufacturer(productDto.getManufacturer());
        product.setImageUrl(productDto.getImageUrl());

//        Category category = new Category();
//        category.setName(productDto.getCategory());
//        List products = new ArrayList();
//        products.add(product);
//        category.setProducts(products);
        Category category = categoryRepository.findByName(productDto.getCategory());
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found");
        }

        product.setCategory(category);

        categoryRepository.save(category);

        productRepository.save(product);

        return product;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Product updateProduct(ProductDto productDto) throws ProductNotFoundException, BadRequestException, IOException {

        Product existingProduct = productRepository.
                findByManufacturerAndAndName(productDto.getManufacturer(), productDto.getName());

        if (existingProduct == null) {
            throw new ProductNotFoundException(productDto.getName() + " does not have in the product list");
        } else {

            existingProduct.setName(productDto.getName());
            existingProduct.setManufacturer(productDto.getManufacturer());
            existingProduct.setImageUrl(productDto.getImageUrl());

//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
//            Path uploadDir = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
//            Path filePath = uploadDir.resolve(fileName);
//            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            existingProduct.setImageUrl(filePath.toString());

            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());

            return productRepository.save(existingProduct);
        }

    }

//    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
//    public void updateProduct(Long productId, ProductDto productDto) throws ProductNotFoundException, CategoryNotFoundException {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//        // Update product fields
//        product.setName(productDto.getName());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setImageUrl(productDto.getImageUrl());
//        product.setManufacturer(productDto.getManufacturer());
//
//        // Set the category if it exists in the DTO
//        if (productDto.getCategory() != null) {
//            Category category = categoryRepository.findByName(productDto.getCategory());
//            if (category == null) {
//                throw new CategoryNotFoundException("Category not found");
//            }
//            product.setCategory(category);
//        } else {
//            product.setCategory(null);
//        }
//        productRepository.save(product);
//    }

    public Product viewProductById(Long productId) throws ProductNotFoundException {

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            return existingProduct.get();
        } else {
            throw new ProductNotFoundException("Product not found with id : " + productId);
        }

    }

    public List<Product> last5productAdded(){
        List<Product> list = productRepository.findAll();
        List<Product> lastFive = list.subList(list.size() - 2, list.size());

        return lastFive;
    }

    public List<Product> viewProductsByCategory(String categoryName)
            throws CategoryNotFoundException, ProductNotFoundException {
        Category existingCategory = categoryRepository.findByName(categoryName);

        if (existingCategory == null) {
            throw new CategoryNotFoundException("Category Name : " + categoryName + " does not exists...");
        } else {
            List<Product> productList = productRepository.findByCategory(existingCategory);
            if (productList.size() == 0) {
                throw new ProductNotFoundException("Product does not exists with Category Name : " + categoryName);
            } else {
                return productList;
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDto> viewProductsByCategory(Long categoryId) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        List<Product> products = productRepository.findByCategory(category);
        return products.stream().map(ProductDto::fromProduct).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);

        return product;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Product addCategoryToProduct(Long productId, String categoryName) throws CategoryNotFoundException, ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found");
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

}
