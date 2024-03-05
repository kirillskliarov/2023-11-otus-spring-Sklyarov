export interface Link {
    route: string;
    label: string;
}

export const links: Link[] = [
    {
        route: 'genre',
        label: 'Genres'
    },
    {
        route: 'author',
        label: 'Authors'
    },
    {
        route: 'book',
        label: 'Books'
    },
];
