package com.andreanesos.librarydatamanager.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorie")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long id;
    
    @Column(name = "nome", unique = true, nullable = true)
    private String name;

    
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "categories")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
    
    protected Category() {
        
    }
    
    public Category(String name) {
        this.name = name;
    }
    
  //getter/setters
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Set<Book> getBooks() {
        return books;
      }

      public void setBooks(Set<Book> tutorials) {
        this.books = tutorials;
      }  

    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }
    
    
    
    
   

}
