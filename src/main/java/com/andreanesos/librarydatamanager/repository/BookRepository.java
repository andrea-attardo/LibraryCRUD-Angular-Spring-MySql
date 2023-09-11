package com.andreanesos.librarydatamanager.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andreanesos.librarydatamanager.model.Book;
import com.andreanesos.librarydatamanager.model.Category;

/*esempoi sql query
SELECT libri.titolo, categorie.nome
FROM libri
JOIN book_categories ON libri.book_id = book_categories.book_id
JOIN categorie ON book_categories.category_id = categorie.category_id;
*/

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findByAvailable(boolean available);
    List<Book> findByTitleContaining(String title);
    //Stream<String> findCategoriesNameByCategories(Set<Category> categories);
    //List<Category> findByCategoriesContains(Set<Category> categories);
    List<Book> findBooksByCategoriesId(Long categoryId);
    
    //aggiungere find by category ecc
}
