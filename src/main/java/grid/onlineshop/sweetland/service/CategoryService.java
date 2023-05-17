package grid.onlineshop.sweetland.service;

import grid.onlineshop.sweetland.dto.request.AddCategoryDto;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.categoryexc.CategoryNotFoundException;
import grid.onlineshop.sweetland.model.Category;
import grid.onlineshop.sweetland.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(AddCategoryDto categoryDto) throws CategoryAlreadyExistsException {
        if (categoryRepository.findByName(categoryDto.getName()) != null) {
            throw new CategoryAlreadyExistsException("Category Already Exists");
        }

        Category category = new Category();

        category.setName(categoryDto.getName());

        return categoryRepository.save(category);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public Category removeCategory(String categoryName) throws CategoryNotFoundException {
        if (categoryRepository.findByName(categoryName)==null)
            throw new CategoryNotFoundException("Category Does Not Exist...");

        Category categoryToDelete = categoryRepository.findByName(categoryName);
        categoryRepository
                .delete(categoryRepository.findByName(categoryName));

        return categoryToDelete;
    }

    public List<Category> getAllCategory() throws CategoryNotFoundException {
        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.size() == 0) {
            throw new CategoryNotFoundException("Category not exists...");
        } else {
            return categoryList;
        }
    }

    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new CategoryNotFoundException("Category with this ID Not Found ");
        }
    }



}
