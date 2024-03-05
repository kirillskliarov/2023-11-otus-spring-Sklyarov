import { Author } from "./author";
import { Comment } from "./comment";
import { Genre } from "./genre";

export interface Book {
    id: number;
    title: string;
    genre: Genre;
    author: Author;
    comments: Comment[];
}
