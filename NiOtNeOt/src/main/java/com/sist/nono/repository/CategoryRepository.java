package com.sist.nono.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
