package grid.onlineshop.sweetland.controller_thyme;

import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.dto.request.AddProductToCartDto;
import grid.onlineshop.sweetland.dto.request.ProductDto;
import grid.onlineshop.sweetland.exceptions.productexc.ProductNotFoundException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.Product;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.service.AuthenticationService;
import grid.onlineshop.sweetland.service.ProductService;
import grid.onlineshop.sweetland.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final JwtService jwtService;

    private final UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/admin/products/new-product")
    public String showNewProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "new-product";
    }

    @GetMapping("/products")
    public String products(HttpServletRequest request, Model model) throws UserNotFoundException, ProductNotFoundException {
        String jwtToken = getCookie(request);
        if (jwtToken != null) {

            String username = jwtService.extractUsername(jwtToken);
            System.out.println(username);

            User user = userService.getByEmail(username);

            List<Product> products = productService.viewAllProducts();

            model.addAttribute("products", products);
            return "products";
        } else {
            return "redirect:/login";
        }
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/products/new-product")
    public String createProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("image") MultipartFile imageUrl,
                                RedirectAttributes redirectAttributes) throws Exception {

        logger.info("Entered add-product method");

        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get("src", "main", "resources", "static", "img", imageUrl.getOriginalFilename());
        fileNames.append(imageUrl.getOriginalFilename());
        Files.write(fileNameAndPath, imageUrl.getBytes());

        logger.info("Uploaded image");

        String image = "/img/" + fileNames;

        productDto.setImageUrl(image);

        productService.addProduct(productDto);

        redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
        return "redirect:/products";
    }


    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws ProductNotFoundException {

        String jwtToken = getCookie(request);
        if (jwtToken != null) {

            String username = jwtService.extractUsername(jwtToken);
            Optional<Product> optionalProduct = Optional.ofNullable(productService.viewProductById(id));
            AddProductToCartDto addProductToCartDto = new AddProductToCartDto();
            addProductToCartDto.setProductId(id);


//            Cookie productCookie = new Cookie("prId", id.toString());
//            productCookie.setHttpOnly(true);
//            response.addCookie(productCookie);

//            System.out.println(productCookie.getValue());

            model.addAttribute("product", optionalProduct);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                model.addAttribute("product", product);
                model.addAttribute("addProductToCartDto", addProductToCartDto);
                return "view-product";
            } else {
                return "products";
            }
        }
        return "view-product";

    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> filteredProducts = productService.searchProducts(query);
        model.addAttribute("products", filteredProducts);

        return "products";
    }


    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwtToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        return jwtToken;
    }

}

