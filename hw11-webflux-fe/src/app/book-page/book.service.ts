import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Book } from "../models/book";
import { BookDto } from "../models/book-dto";
import { BookShort } from "../models/book-short";
import { CommentDto } from "../models/comment-dto";

@Injectable()
export class BookService {
    constructor(
        private readonly httpClient: HttpClient
    ) {}

    public getAll(): Observable<BookShort[]> {
        return this.httpClient.get<Book[]>('/api/books');
    }

    public getById(id: number): Observable<Book> {
        return this.httpClient.get<Book>(`/api/books/${id}`);
    }

    public create(book: BookDto): Observable<Book> {
        return this.httpClient.post<Book>('/api/books', book);
    }

    public edit(id: number, book: BookDto): Observable<Book> {
        return this.httpClient.post<Book>(`/api/books/${id}`, book);
    }

    public createComment(bookId: number, comment: CommentDto): Observable<number> {
        return this.httpClient.post<number>(`/api/books/${bookId}/comment`, comment);
    }

    public delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`/api/books/${id}`);
    }
}
