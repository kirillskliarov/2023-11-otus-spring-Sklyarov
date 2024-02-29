import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, OnInit } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { BookService } from "../book.service";
import { Observable } from "rxjs";
import { AsyncPipe, NgFor } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import { BookShort } from "../../models/book-short";
import { BookFormComponent } from "../book-form/book-form.component";
import { CardComponent } from "../../common/card/card.component";
import { ItemComponent } from "../../common/item/item.component";

@Component({
    selector: 'app-book-list',
    templateUrl: 'book-list.component.html',
    styleUrls: ['book-list.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [BookService,],
    imports: [NgFor, AsyncPipe, HttpClientModule, RouterModule, ReactiveFormsModule, BookFormComponent, CardComponent, ItemComponent],
})
export class BookListComponent implements OnInit {
    public books$: Observable<BookShort[]> = this.bookService.getAll();
    constructor(
        private readonly bookService: BookService,
        private readonly destroyRef: DestroyRef,
        private readonly cdr: ChangeDetectorRef,
    ) {}

    public ngOnInit(): void {
        this.refresh();
    }
    public refresh(): void {
        this.books$ = this.bookService.getAll();
    }

    public delete(id: number): void {
        this.bookService.delete(id).pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
            next: () => {
                this.refresh();
                this.cdr.markForCheck();
            },
        });
    }
}
