package com.g1RssApi.services;

import com.g1RssApi.dtos.CategoryDTO;
import com.g1RssApi.models.CategoryModel;
import com.g1RssApi.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired // Injecao de dependencias
    private CategoryRepository categoryRepository;

    /**
     * Cria uma nova categoria.
     *
     * @param categoryDTO dto com as informacoes da categoria.
     * @return ResponseEntity com status criado.
     */
    public ResponseEntity createCategory(CategoryDTO categoryDTO) {
        CategoryModel newCategory = new CategoryModel();
        BeanUtils.copyProperties(categoryDTO, newCategory);
        this.categoryRepository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Busca todas as categorias cadastradas.
     *
     * @return ResponseEntity com as categorias.
     */
    public ResponseEntity getAllCategories() {
        List<CategoryModel> list = this.categoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * Busca uma categoria conforme um id recebido por parametro.
     *
     * @param id id da categoria a ser buscada.
     * @return ResponseEntity.OK se a categoria for encontrada
     * ou ResponseEntity.NOT_FOUND se a categoria nao for encontrada.
     */
    public ResponseEntity getCategory(Long id) {
        Optional<CategoryModel> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalCategory);
    }

    /**
     * Atualiza uma categoria conforme id e dto recebidos por parametro.
     *
     * @param id          id da categoria a ser atualizada.
     * @param categoryDTO dto com as informacoes da categoria a ser atualizada.
     * @return ResponseEntity.OK se a categoria for encontrada e atualizada
     * ou ResponseEntity.NOT_FOUND se a categoria nao for encontrada.
     */
    public ResponseEntity updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<CategoryModel> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CategoryModel categoryObject = optionalCategory.get();
        BeanUtils.copyProperties(categoryDTO, categoryObject);
        this.categoryRepository.save(categoryObject);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Exclui uma categoria conforme id recebido por parametro.
     *
     * @param id id da categoria a ser excluida.
     * @return ResponseEntity.OK se a categoria for encontrada e excluida
     * ou ResponseEntity.NOT_FOUND se a categoria nao for encontrada.
     */
    public ResponseEntity deleteCategory(Long id) {
        Optional<CategoryModel> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CategoryModel categoryObject = optionalCategory.get();
        this.categoryRepository.delete(categoryObject);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
