package com.andreanesos.librarydatamanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreanesos.librarydatamanager.model.Category;

//serve questa repository?
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    //Category findByName(String name);
    List<Category> findCategoriesByBooksId(Long book_Id);
    Optional<Category> findByNameIgnoringCase(String name);
    

}
