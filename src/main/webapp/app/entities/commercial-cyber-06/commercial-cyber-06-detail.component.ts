import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialCyber06 } from 'app/shared/model/commercial-cyber-06.model';

@Component({
  selector: 'jhi-commercial-cyber-06-detail',
  templateUrl: './commercial-cyber-06-detail.component.html',
})
export class CommercialCyber06DetailComponent implements OnInit {
  commercial: ICommercialCyber06 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commercial }) => (this.commercial = commercial));
  }

  previousState(): void {
    window.history.back();
  }
}
