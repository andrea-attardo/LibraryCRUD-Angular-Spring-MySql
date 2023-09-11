package com.andreanesos.librarydatamanager.model;



import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libri")
public class Book {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "book_id")
    private Long id;
    
    @Column(name = "titolo")
    private String title;
    
    @Column(name = "autore")
    private String author;
    
    
    /*studiare bene realazioni tabelle
     * https://github.com/spring-projects/spring-data-book/blob/master/jpa/src/main/java/com/oreilly/springdata/jpa/core/Customer.java
     * https://www.javatpoint.com/dbms-er-model-concept
     * ci sono vicino, seistono i valori ma non appare la colonna, è normale?
     * 
     * praticamente setto le relazioni fra entità NON e' UN FIELD PROPRIO DELLA TABELLA
     */
    @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
    @JoinTable(name = "book_categories",
        joinColumns = { @JoinColumn(name = "book_id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id")}/*,
        uniqueConstraints = {@UniqueConstraint(
            columnNames = {"book_id", "category_id"})}*/)
    private Set<Category> categories = new HashSet<>();
    
    //cambiare con availabilty
    //?private Availability availablilty;
    
    @Column(name = "Disponibilità")
    private boolean available;
    
    protected Book() {
    }

    public Book(String title, String author, /*Set<Category> categories,*/ boolean available) {
        this.title = title;
        this.author = author;
        //this.categories = categories;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public void addCategory(Category category) {
        //System.out.println(this.getClass().getName() +  " addCategory() called");
        this.categories.add(category);
        category.getBooks().add(this);
      }
    
    
    public void removeCategory(Long categoryId) {
        Optional<Category> categoryOptional = this.categories.stream()
                                                .filter(cat -> cat.getId() == categoryId).findFirst();
        
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            this.categories.remove(category);
            category.getBooks().remove(this);
        }
    }
    public void removeAllCategories() {
        this.categories.removeAll(this.categories);
    }
    
    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", categories=" + categories + ", available="
                + available + "]";
    }
    
    
    
    
}
