import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDevisCyber06 } from 'app/shared/model/devis-cyber-06.model';

@Component({
  selector: 'jhi-devis-cyber-06-detail',
  templateUrl: './devis-cyber-06-detail.component.html',
})
export class DevisCyber06DetailComponent implements OnInit {
  devis: IDevisCyber06 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ devis }) => (this.devis = devis));
  }

  previousState(): void {
    window.history.back();
  }
}
