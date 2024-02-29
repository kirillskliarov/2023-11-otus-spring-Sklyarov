import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, OnInit, WritableSignal, signal } from "@angular/core";
import { BookService } from "./../book.service";
import { Observable, defer, map, switchMap } from "rxjs";
import { Book } from "../../models/book";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { BookFormComponent } from "../book-form/book-form.component";
import { CardComponent } from "../../common/card/card.component";
import { ItemComponent } from "../../common/item/item.component";

@Component({
    selector: 'app-book',
    templateUrl: 'book.component.html',
    styleUrls: ['book.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [BookService],
    imports: [NgIf, NgFor, AsyncPipe, HttpClientModule, ReactiveFormsModule, BookFormComponent, CardComponent, ItemComponent],
})
export class BookComponent implements OnInit {
    public book$!: Observable<Book>;
    public readonly isEdit: WritableSignal<boolean> = signal<boolean>(false);
    public readonly commentForm = new FormGroup({
        text: new FormControl<string>('', Validators.required),
    });
    constructor(
        private readonly bookService: BookService,
        private readonly activatedRoute: ActivatedRoute,
        private readonly destroyRef: DestroyRef,
        private readonly cdr: ChangeDetectorRef,
    ) {}

    public ngOnInit(): void {
        this.load();
    }
    public load(): void {
        this.book$ = this.activatedRoute.paramMap.pipe(
            map(paramMap => Number(paramMap.get('id'))),
            switchMap(id => defer(() => this.bookService.getById(id))),
        );
    }
    public setIsEdit(isEdit: boolean): void {
        this.isEdit.set(isEdit);
    }

    public onBookSubmitted(): void {
        this.setIsEdit(false);
        this.load();
    }
    
    public createComment(): void {
        this.book$?.pipe(
            switchMap(book => defer(() => this.bookService.createComment(book.id, {text: this.commentForm.controls.text.value ?? ''}))),
            takeUntilDestroyed(this.destroyRef),
        ).subscribe({
            next: () => {
                this.commentForm.reset();
                this.commentForm.updateValueAndValidity();
                this.load();
                this.cdr.detectChanges();
            },
        });
    }
}
