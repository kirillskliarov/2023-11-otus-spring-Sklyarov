import { ChangeDetectionStrategy, Component } from "@angular/core";
import { RouterModule } from "@angular/router";

@Component({
    selector: 'app-book-page',
    templateUrl: 'book-page.component.html',
    styleUrls: ['book-page.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    imports: [RouterModule],
})
export class BookPageComponent {

}
