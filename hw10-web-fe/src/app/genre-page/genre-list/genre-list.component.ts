import { ChangeDetectionStrategy, ChangeDetectorRef, Component, DestroyRef, OnInit } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { GenreService } from "../genre.service";
import { Observable } from "rxjs";
import { Genre } from "../../models/genre";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ItemComponent } from "../../common/item/item.component";
import { CardComponent } from "../../common/card/card.component";

@Component({
    selector: 'app-genre-list',
    templateUrl: 'genre-list.component.html',
    styleUrls: ['genre-list.component.html'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [GenreService],
    imports: [NgFor, NgIf, AsyncPipe, HttpClientModule, RouterModule, ReactiveFormsModule, ItemComponent, CardComponent],
})
export class GenreListComponent implements OnInit {
    public genres$: Observable<Genre[]> | null = null;
    public genreForm = new FormGroup({
        name: new FormControl<string>('', Validators.required),
    });
    constructor(
        private readonly genreService: GenreService,
        private readonly destroyRef: DestroyRef,
        private cdr: ChangeDetectorRef,
    ) {}

    public ngOnInit(): void {
        this.refresh();
    }
    public refresh(): void {
        this.genres$ = this.genreService.getAll();
    }

    public submit(): void {
        this.genreService.create({
            name: this.genreForm.controls.name.value ?? '',
        }).pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
            next: () => {
                this.genreForm.controls.name.reset();
                this.genreForm.controls.name.updateValueAndValidity();
                this.refresh();
                this.cdr.detectChanges();
            },
        });
    }
}
