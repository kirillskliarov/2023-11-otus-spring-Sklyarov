import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'genre',
        pathMatch: 'full',
    },
    {
        path: 'genre',
        loadComponent: () => import('./genre-page/genre-page.component').then(m => m.GenrePageComponent),
        children: [
            {
                path: '',
                loadComponent: () => import('./genre-page/genre-list/genre-list.component').then(m => m.GenreListComponent),
            },
            {
                path: ':id',
                loadComponent: () => import('./genre-page/genre/genre.component').then(m => m.GenreComponent),
            },
        ],
    },
    {
        path: 'author',
        loadComponent: () => import('./author-page/author-page.component').then(m => m.AuthorPageComponent),
        children: [
            {
                path: '',
                loadComponent: () => import('./author-page/author-list/author-list.component').then(m => m.AuthorListComponent),
            },
            {
                path: ':id',
                loadComponent: () => import('./author-page/author/author.component').then(m => m.AuthorComponent),
            },
        ],
    },
    {
        path: 'book',
        loadComponent: () => import('./book-page/book-page.component').then(m => m.BookPageComponent),
        children: [
            {
                path: '',
                loadComponent: () => import('./book-page/book-list/book-list.component').then(m => m.BookListComponent),
            },
            {
                path: ':id',
                loadComponent: () => import('./book-page/book/book.component').then(m => m.BookComponent),
            },
        ],
    },
];
