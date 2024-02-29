import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, OnInit } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AuthorService } from "../author.service";
import { Observable } from "rxjs";
import { Author } from "../../models/author";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ItemComponent } from "../../common/item/item.component";
import { CardComponent } from "../../common/card/card.component";

@Component({
    selector: 'app-genre-list',
    templateUrl: 'author-list.component.html',
    styleUrls: ['author-list.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [AuthorService],
    imports: [NgFor, NgIf, AsyncPipe, HttpClientModule, RouterModule, ReactiveFormsModule, ItemComponent, CardComponent],
})
export class AuthorListComponent implements OnInit {
    public authors$: Observable<Author[]> | null = null;
    public authorForm = new FormGroup({
        fullName: new FormControl<string>('', Validators.required),
    });
    constructor(
        private readonly authorService: AuthorService,
        private readonly destroyRef: DestroyRef,
        private cdr: ChangeDetectorRef,
    ) {}

    public ngOnInit(): void {
        this.refresh();
    }
    public refresh(): void {
        this.authors$ = this.authorService.getAll();
    }

    public submit(): void {
        this.authorService.create({
            fullName: this.authorForm.controls.fullName.value ?? '',
        }).pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
            next: () => {
                this.authorForm.controls.fullName.reset();
                this.authorForm.controls.fullName.updateValueAndValidity();
                this.refresh();
                this.cdr.detectChanges();
            },
        });
    }
}
