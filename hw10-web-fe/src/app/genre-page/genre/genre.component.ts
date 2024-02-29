import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { GenreService } from "./../genre.service";
import { Observable, defer, map, switchMap } from "rxjs";
import { Genre } from "../../models/genre";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { CardComponent } from "../../common/card/card.component";
import { ItemComponent } from "../../common/item/item.component";

@Component({
    selector: 'app-genre',
    templateUrl: 'genre.component.html',
    styleUrls: ['genre.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [GenreService],
    imports: [NgIf, AsyncPipe, HttpClientModule, CardComponent, ItemComponent],
})
export class GenreComponent implements OnInit {
    public genre$: Observable<Genre> | null = null;
    constructor(
        private readonly genreService: GenreService,
        private readonly activatedRoute: ActivatedRoute,
    ) {}

    public ngOnInit(): void {
        this.load();
    }
    public load(): void {
        this.genre$ = this.activatedRoute.paramMap.pipe(
            map(paramMap => Number(paramMap.get('id'))),
            switchMap(id => defer(() => this.genreService.getById(id))),
        );
    }
}
