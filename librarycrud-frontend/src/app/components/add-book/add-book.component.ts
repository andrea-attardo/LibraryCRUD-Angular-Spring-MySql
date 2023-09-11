import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/models/book.model';
import { Category } from 'src/app/models/category.model';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})

export class AddBookComponent implements OnInit{
    
    book: Book = {
      title: '',
      author: '',
      categories: [],
      available: false
    };
    categoriesValues: any;
    submitted = false;
    
    constructor(private bookService: BookService){}
    
    ngOnInit(): void {}
    
    saveBook(): void {
        
        this.categoriesValues = this.categoriesValues.split(", ");
        
        const data = {
            title: this.book.title,
            author: this.book.author,
            categories: Array.from(this.categoriesValues)
        };
        
        //console.log(data); //show post json
        
        this.bookService.create(data)
        	.subscribe({
        	    next: (res) => {
        	        console.log(res);
        	        this.submitted = true;
        	    },
        	    error: (e) => console.error(e)
        	});
    }
    
    newBook(): void {
        this.submitted = false;
        this.book = {
            title: '',
            author: '',
            categories: [],
            available: false
        };
        this.categoriesValues = '';
    }
}

