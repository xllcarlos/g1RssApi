package com.g1RssApi.controllers;

import com.g1RssApi.dtos.CategoryDTO;
import com.g1RssApi.dtos.NoticeDTO;
import com.g1RssApi.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired // Injecao de dependencias
    private CategoryService categoryService;

    /**
     * Rota para cadastrar de uma nova categoria.
     *
     * @param categoryDTO dto com informacoes da categoria.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @PostMapping
    public ResponseEntity createNotice(@RequestBody @Valid CategoryDTO categoryDTO) {
        return this.categoryService.createCategory(categoryDTO);
    }

    /**
     * Rota para retornar todas as categorias cadastradas.
     *
     * @return ResponseEntity provido pelo NoticeService.
     */
    @GetMapping
    public ResponseEntity getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    /**
     * Rota para retornar uma categoria conforme id recebido na URL.
     *
     * @param id id da noticia a ser buscada.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @GetMapping("/{id}")
    public ResponseEntity getCategory(@PathVariable(name = "id") Long id) {
        return this.categoryService.getCategory(id);
    }

    /**
     * Rota para atualizar uma categoria.
     * @param id id da categoria a ser atualizada.
     * @param categoryDTO dto com informacoes da categoria.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        return this.categoryService.updateCategory(id, categoryDTO);
    }

    /**
     * Rota para excluir uma categoria.
     * @param id id da categoria a ser excluida.
     * @return ResponseEntity provido pelo NoticeService.
     */
    @DeleteMapping("{id}")
    public ResponseEntity deleteCategory(@PathVariable(name = "id") Long id) {
        return this.categoryService.deleteCategory(id);
    }

}
