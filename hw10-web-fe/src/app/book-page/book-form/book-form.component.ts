import { ChangeDetectionStrategy, Component, DestroyRef, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { Book } from "../../models/book";
import { BookService } from "../book.service";
import { AuthorService } from "../../author-page/author.service";
import { GenreService } from "../../genre-page/genre.service";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Observable, map } from "rxjs";
import { Author } from "../../models/author";
import { Genre } from "../../models/genre";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { BookDto } from "../../models/book-dto";
import { CardComponent } from "../../common/card/card.component";
import { ItemComponent } from "../../common/item/item.component";

@Component({
    selector: 'app-book-form',
    templateUrl: 'book-form.component.html',
    styleUrls: ['book-form.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [BookService, AuthorService, GenreService],
    imports: [AsyncPipe, NgFor, NgIf, ReactiveFormsModule, CardComponent, ItemComponent],
})
export class BookFormComponent implements OnChanges {
    @Input()
    public book: Book | null = null;
    @Output()
    public readonly submitted = new EventEmitter<void>();

    public authors$: Observable<Author[]> = this.authorService.getAll();
    public genres$: Observable<Genre[]> = this.genreService.getAll();
    public bookForm = new FormGroup({
        title: new FormControl<string>('', Validators.required),
        authorId: new FormControl<number | null>(null, Validators.required),
        genreId: new FormControl<number | null>(null, Validators.required),
    });

    constructor(
        private readonly bookService: BookService,
        private readonly authorService: AuthorService,
        private readonly genreService: GenreService,
        private readonly destroyRef: DestroyRef,
    ) {}

    public ngOnChanges(changes: SimpleChanges): void {
        if (changes['book']) {
            this.bookForm.reset({
                title: this.book?.title,
                authorId: this.book?.author.id,
                genreId: this.book?.genre.id,
            });
            this.bookForm.updateValueAndValidity();
        }
    }

    public submit(): void {
        const bookDto: BookDto = {
            title: this.bookForm.controls.title.value ?? '',
            authorId: this.bookForm.controls.authorId.value ?? 0,
            genreId: this.bookForm.controls.genreId.value ?? 0,
        };
        const postRequest = this.book ? this.bookService.edit(this.book.id, bookDto) : this.bookService.create(bookDto);
       
        postRequest.pipe(
            takeUntilDestroyed(this.destroyRef)).subscribe({
            next: () => {
                this.bookForm.reset();
                this.bookForm.updateValueAndValidity();
                this.submitted.emit();
            },
        });
    }
}
