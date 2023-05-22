package grid.onlineshop.sweetland.controller;

import grid.onlineshop.sweetland.dto.request.ProductDto;
import grid.onlineshop.sweetland.exceptions.BadRequestException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryNotFoundException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.model.Product;
import grid.onlineshop.sweetland.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product) throws ProductAlreadyExistsException {

        return new ResponseEntity<Product>(productService.addProduct(product), HttpStatus.CREATED);

    }


    @GetMapping("/")
    public ResponseEntity<List<Product>> viewAllProducts() throws ProductNotFoundException {

        return new ResponseEntity<List<Product>>(productService.viewAllProducts(), HttpStatus.OK);

    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto product) throws BadRequestException, ProductNotFoundException {

        return new ResponseEntity<Product>(productService.updateProduct(product), HttpStatus.OK);

    }


    @GetMapping("/viewProductById/{pid}")
    public ResponseEntity<Product> viewProductById(@PathVariable("pid") Long productId) throws ProductNotFoundException {
        return new ResponseEntity<Product>(productService.viewProductById(productId), HttpStatus.OK);
    }


    @GetMapping("/viewProductsByCategory/{cname}")
    public ResponseEntity<List<Product>> viewProductsByCategory(@PathVariable("cname") String cname) throws CategoryNotFoundException, ProductNotFoundException {

        return new ResponseEntity<List<Product>>(productService.viewProductsByCategory(cname), HttpStatus.OK);

    }


    @DeleteMapping("/deleteProductById/{pid}")
    public ResponseEntity<Product> removeProduct(@PathVariable("pid") Long productId) throws ProductNotFoundException {

        return new ResponseEntity<Product>(productService.deleteProduct(productId) ,HttpStatus.OK);

    }

    @PutMapping("/addProdToCat/{pid}/{cname}")
    public ResponseEntity<Product> addProductToACategory(@PathVariable("pid") Long productID, @PathVariable("cname") String categoryName) throws CategoryNotFoundException, ProductNotFoundException {

        return new ResponseEntity<Product>(productService.addCategoryToProduct(productID, categoryName), HttpStatus.CREATED);

    }


}
