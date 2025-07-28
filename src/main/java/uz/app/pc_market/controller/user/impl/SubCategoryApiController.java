package uz.app.pc_market.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.app.pc_market.entity.SubCategory;
import uz.app.pc_market.repository.SubCategoryRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubCategoryApiController {
    private final SubCategoryRepository subCategoryRepository;

    @GetMapping("/subcategories")
    public List<SubCategory> getSubCategories(@RequestParam("categoryId") Long categoryId) {
        return subCategoryRepository.findAllByCategory_Id(categoryId);
    }
}
