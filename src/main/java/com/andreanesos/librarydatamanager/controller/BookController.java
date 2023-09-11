package com.andreanesos.librarydatamanager.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.andreanesos.librarydatamanager.model.Book;
import com.andreanesos.librarydatamanager.model.Category;
import com.andreanesos.librarydatamanager.repository.BookRepository;
import com.andreanesos.librarydatamanager.repository.CategoryRepository;
import com.fasterxml.jackson.core.sym.Name;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    
    @GetMapping("/api/books")
    public ResponseEntity<List<Book>> findAll(@RequestParam(required = false) String title){
        try {
            List<Book> books;
            
            if (title == null) {
                books = bookRepository.findAll();
            }else {
                books = bookRepository.findByTitleContaining(title);
            }
            
            if(books.isEmpty()) return ResponseEntity.noContent().build();
            
            return ResponseEntity.ok(books); 
            
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/api/books/{id}")
    ResponseEntity<Book> findById(@PathVariable("id") Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //devo fare anche cateogryController per creare le categorie?Oppure faccio gìà preimpostate
    @PostMapping("/api/books")
    public ResponseEntity<Void> save(@RequestBody Book bookRequest, UriComponentsBuilder ucb){
        try {
            Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.isAvailable());   
                
            bookRequest.getCategories()
                     .forEach( categoryRequest -> {
                         Optional<Category> categoryInRepo = categoryRepository.findByNameIgnoringCase(categoryRequest.getName());
                         
                         Category category;
                         //&& book.getCategories().contains(categoryRequest)
                         if(categoryInRepo.isPresent() ) {
                             //System.out.println(categoryRequest.getName() + " is present");
                             category = categoryInRepo.get();
                         }else {
                             category = categoryRepository.save(categoryRequest);
                         }
                         
                             book.addCategory(category);
                         });
            
            bookRepository.save(book);
             
            //se voglio salvare qui anche le categorie devo avere una reference alle catogorie dentro categoryrepository
            //per fare controlli come postmapping dentro categorycontroller
            
            URI locationOfBook = ucb
                    .path("/api/books/{id}")
                    .buildAndExpand(book.getId())
                    .toUri();
                    
            return ResponseEntity.created(locationOfBook).build();
            
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @PutMapping("/api/books/{id}")
    //era httpstatus fra <>
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book bookRequest){
        try {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if(bookOptional.isPresent()) {
               Book book = bookOptional.get();
               book.setTitle(bookRequest.getTitle());
               book.setAuthor(bookRequest.getAuthor());
               book.setAvailable(bookRequest.isAvailable());
               
               //manage categories
               book.removeAllCategories();
               
               bookRequest.getCategories()
                   .forEach( categoryRequest -> {
                       Optional<Category> categoryInRepo = categoryRepository.findByNameIgnoringCase(categoryRequest.getName());
                       
                       Category category;
                       //&& book.getCategories().contains(categoryRequest)
                       if(categoryInRepo.isPresent() ) {
                           //System.out.println(categoryRequest.getName() + " is present");
                           category = categoryInRepo.get();
                       }else {
                           category = categoryRepository.save(categoryRequest);
                       }
                       
                           book.addCategory(category);
                       });
               
               bookRepository.save(book);
               
               //return ResponseEntity.ok().build();
               return ResponseEntity.ok(book);
                
            }else {
                return ResponseEntity.badRequest().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @DeleteMapping("/api/books")
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            bookRepository.deleteAll();
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id){
        try {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/api/books/availability")
    public ResponseEntity<List<Book>> findByAvailability(){
        try {
            List<Book> books =  bookRepository.findByAvailable(true);
            
            if(books.isEmpty()) return ResponseEntity.noContent().build();
            
            return ResponseEntity.ok(books); 
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}





















