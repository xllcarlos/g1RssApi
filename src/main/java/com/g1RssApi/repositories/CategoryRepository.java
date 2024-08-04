package com.g1RssApi.repositories;

import com.g1RssApi.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Gilson Teixeira
 */
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    Optional<CategoryModel> findById(Long id);

}
