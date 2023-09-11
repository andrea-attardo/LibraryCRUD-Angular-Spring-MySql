import {Category} from "./category.model";

export class Book {
    id?: any;
    title?: string;
    author?: string;
    categories?: Category[];
    available?: boolean;
}
