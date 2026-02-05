package com.junwoo.catchtablebackend.domain.category.repository;

import com.junwoo.catchtablebackend.domain.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
