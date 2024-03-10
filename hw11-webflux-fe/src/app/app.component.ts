import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { links } from './constants/links';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, NgFor],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  public readonly links = links;
  title = 'hw10-web-fe';
}
