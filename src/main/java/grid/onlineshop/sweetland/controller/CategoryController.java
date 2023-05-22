package grid.onlineshop.sweetland.controller;

import java.util.List;

import grid.onlineshop.sweetland.dto.request.AddCategoryDto;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryNotFoundException;
import grid.onlineshop.sweetland.model.Category;
import grid.onlineshop.sweetland.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
	
	
	private final CategoryService categoryService;

	
//	@PostMapping("/addCat")
//	public ResponseEntity<Category> addCategory(@RequestBody AddCategoryDto categoryDto) throws CategoryAlreadyExistsException {
//
//		return new ResponseEntity<Category>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
//
//	}
//
	
	@DeleteMapping("/removeCat/{cname}")
	public ResponseEntity<Category> removeCategory(@PathVariable("cname") String categoryName) throws CategoryNotFoundException {
		
		return new ResponseEntity<Category>(categoryService.removeCategory(categoryName), HttpStatus.OK);
	
	}
	
	@GetMapping("/getAllCat")
	public ResponseEntity<List<Category>> getAllCategory() throws CategoryNotFoundException {
		
		return new ResponseEntity<List<Category>>(categoryService.getAllCategory(), HttpStatus.ACCEPTED);
	
	}
	
	@GetMapping("/getCatById/{catId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("catId") Long catId) throws CategoryNotFoundException {
		
		return new ResponseEntity<Category>(categoryService.getCategoryById(catId), HttpStatus.ACCEPTED);
	
	}
	

}
