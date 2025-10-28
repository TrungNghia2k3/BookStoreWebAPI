package com.ntn.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ntn.ecommerce.mapper.CategoryMapper;
import com.ntn.ecommerce.repository.CategoryRepository;
import com.ntn.ecommerce.repository.ProductRepository;
import com.ntn.ecommerce.utilities.ImageUtilities;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ImageUtilities imageUtilities;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAll() {}
}
