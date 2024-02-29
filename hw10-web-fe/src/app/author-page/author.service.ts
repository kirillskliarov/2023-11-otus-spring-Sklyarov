import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Author } from "../models/author";
import { AuthorDto } from "../models/author-dto";

@Injectable()
export class AuthorService {
    constructor(
        private readonly httpClient: HttpClient
    ) {}

    public getAll(): Observable<Author[]> {
        return this.httpClient.get<Author[]>('/api/authors');
    }

    public getById(id: number): Observable<Author> {
        return this.httpClient.get<Author>(`/api/authors/${id}`);
    }

    public create(author: AuthorDto): Observable<number> {
        return this.httpClient.post<number>('/api/authors', author);
    }
}
