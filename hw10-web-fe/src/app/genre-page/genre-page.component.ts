import { ChangeDetectionStrategy, Component } from "@angular/core";
import { RouterModule } from "@angular/router";

@Component({
    selector: 'app-genre-page',
    templateUrl: 'genre-page.component.html',
    styleUrls: ['genre-page.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    imports: [RouterModule],
})
export class GenrePageComponent {

}
