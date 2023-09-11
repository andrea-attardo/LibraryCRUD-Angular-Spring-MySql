package com.andreanesos.librarydatamanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andreanesos.librarydatamanager.model.Book;
import com.andreanesos.librarydatamanager.model.Category;
import com.andreanesos.librarydatamanager.repository.BookRepository;
import com.andreanesos.librarydatamanager.repository.CategoryRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    BookRepository bookRepository;
    
    @PostMapping("/api/books/{bookId}/categories")
    public ResponseEntity<Category> addCategories(@PathVariable("bookId") Long bookId, @RequestBody Category categoryRequest){
        //Optional<Category> categoryOptional = categoryRepository.findById(categoryRequest.getId() );
        try {//refactoring
            Optional<Category> categoryInRepo = categoryRepository.findByNameIgnoringCase(categoryRequest.getName());
            
            //pure questo check se esiste?
            Book book = bookRepository.findById(bookId).get();
            
            //se c'ho la refarence alla categoria dentro il repoasitory non creo duplicato
            //e non alla category del request
            Category category;
            //&& book.getCategories().contains(categoryRequest)
            if(categoryInRepo.isPresent() ) {
                //System.out.println(categoryRequest.getName() + " is present");
                category = categoryInRepo.get();
            }else {
                category = categoryRepository.save(categoryRequest);
            }
            
            if (book.getCategories().contains(category)) {
                return ResponseEntity.ok(categoryInRepo.get());
            }
            
            book.addCategory(category);
            bookRepository.save(book);
            return ResponseEntity.created(null).build(); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }    
                
    }
    
    
}
