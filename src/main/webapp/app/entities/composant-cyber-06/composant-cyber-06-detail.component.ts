import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComposantCyber06 } from 'app/shared/model/composant-cyber-06.model';

@Component({
  selector: 'jhi-composant-cyber-06-detail',
  templateUrl: './composant-cyber-06-detail.component.html',
})
export class ComposantCyber06DetailComponent implements OnInit {
  composant: IComposantCyber06 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ composant }) => (this.composant = composant));
  }

  previousState(): void {
    window.history.back();
  }
}
