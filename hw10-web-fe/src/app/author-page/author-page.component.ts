import { ChangeDetectionStrategy, Component } from "@angular/core";
import { RouterModule } from "@angular/router";

@Component({
    selector: 'app-author-page',
    templateUrl: 'author-page.component.html',
    styleUrls: ['author-page.component.html'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: true,
    imports: [RouterModule],
})
export class AuthorPageComponent {

}
