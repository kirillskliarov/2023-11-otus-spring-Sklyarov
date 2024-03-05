import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { AuthorService } from "./../author.service";
import { Observable, defer, map, switchMap } from "rxjs";
import { Author } from "../../models/author";
import { AsyncPipe, NgFor, NgIf } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { CardComponent } from "../../common/card/card.component";
import { ItemComponent } from "../../common/item/item.component";

@Component({
    selector: 'app-author',
    templateUrl: 'author.component.html',
    styleUrls: ['author.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    providers: [AuthorService],
    imports: [NgIf, AsyncPipe, HttpClientModule, CardComponent, ItemComponent],
})
export class AuthorComponent implements OnInit {
    public author$: Observable<Author> | null = null;
    constructor(
        private readonly authorService: AuthorService,
        private readonly activatedRoute: ActivatedRoute,
    ) {}

    public ngOnInit(): void {
        this.load();
    }
    public load(): void {
        this.author$ = this.activatedRoute.paramMap.pipe(
            map(paramMap => Number(paramMap.get('id'))),
            switchMap(id => defer(() => this.authorService.getById(id))),
        );
    }
}
