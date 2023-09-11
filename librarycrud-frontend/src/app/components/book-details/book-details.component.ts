import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from 'src/app/services/book.service';
import { Book } from 'src/app/models/book.model';
import { Category } from 'src/app/models/category.model';


@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})

export class BookDetailsComponent {

    @Input() viewMode = false;
    
    @Input() currentBook: Book = {
      title: '',
      author: '',
      categories: [],
      available: false
    };
    categoriesValues: any;
    message = '';
    
    
    //
    constructor(
        private bookService: BookService,
        private route: ActivatedRoute,
        private router: Router
        ){   }
    
    
    ngOnInit(): void {
        if (!this.viewMode) {
            //console.log("ngOnInit called");
            this.message = '';
            this.getBook(this.route.snapshot.params["id"]);
        }
    }
    
    getCategoriesValues(): any{
        if (this.currentBook.categories !== undefined ) {
        	return this.currentBook.categories
            							.map(category => category.name);
   		} else{
   		    return '';
   		}
    }
    
    //
    getBook(id: string): void {
        this.bookService.get(id)
        	.subscribe({
        	    next: (data) => {
        	        this.currentBook = data;
        	        //if (this.currentBook.categories !== undefined) this.categoriesValues = JSON.stringify(this.currentBook.categories);
        	        this.categoriesValues = this.getCategoriesValues();
        	        console.log("availiability durein getBook" + this.currentBook.available);
        	        //console.log(this.currentBook.categories);
        	        console.log(data);
        	    },
	            error: (e) => console.error(e)
        	});
    }
    
    
    //
    updateBook(): void {
        this.message = '';
        
        //funzionava c'è un problema col getbook?
        //this.categoriesValues = this.getCategoriesValues();
        console.log("this.categoriesValues: " + this.categoriesValues);
        //if (this.currentBook.categories !== undefined) console.log("this.curerntBook.categories: " + this.currentBook.categories);
        if (this.categoriesValues.includes(", ")) {
            this.categoriesValues = this.categoriesValues.split(", ");
	    	this.currentBook.categories = Array.from(this.categoriesValues)
        }
        
        //console.log("categiresvalues: " + this.categoriesValues);  
        //console.log("categories: " + this.currentBook.categories);  
                
        this.bookService.update(this.currentBook.id, this.currentBook)
        	.subscribe({
        	  next: (res) => {
        	      console.log(res);
        	      
        	      this.message = res.message ? res.message: 'Questo libro è stato aggiornato con succcesso.';
        	  },
	          error: (e) => console.error(e)
        	});
    }
    
    
    //
    deleteBook(): void {
        this.bookService.delete(this.currentBook.id)
        	.subscribe({
        	    //questo è cosa succede dopo il click
        	    next: (res) => {
        	        console.log(res);
        	        this.router.navigate(['/books']);
        	    },
	            error: (e) => console.error(e)
        	});
    }
    
    
    //
    updateAvailable(status: boolean): void {
		const data = {
	        title: this.currentBook.title,
	        author: this.currentBook.author,
	        available: status,
	        categories: this.currentBook.categories
		};
        
        this.message = '';
        
		this.bookService.update(this.currentBook.id, data)
			.subscribe({
			  next: (res) => {
			      console.log(res);
			      this.currentBook.available = status;
			      this.message = res.message ? res.message : 'La disponibilità è stata aggiornata con successo!';
			  },
        	  error: (e) => console.error(e)
    		});
    }
    
}



















