import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Genre } from "../models/genre";
import { GenreDto } from "../models/genre-dto";

@Injectable()
export class GenreService {
    constructor(
        private readonly httpClient: HttpClient
    ) {}

    public getAll(): Observable<Genre[]> {
        return this.httpClient.get<Genre[]>('/api/genres');
    }

    public getById(id: number): Observable<Genre> {
        return this.httpClient.get<Genre>(`/api/genres/${id}`);
    }

    public create(genre: GenreDto): Observable<number> {
        return this.httpClient.post<number>('/api/genres', genre);
    }
}
