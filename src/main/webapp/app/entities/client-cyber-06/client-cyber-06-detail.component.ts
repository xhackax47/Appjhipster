import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientCyber06 } from 'app/shared/model/client-cyber-06.model';

@Component({
  selector: 'jhi-client-cyber-06-detail',
  templateUrl: './client-cyber-06-detail.component.html',
})
export class ClientCyber06DetailComponent implements OnInit {
  client: IClientCyber06 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => (this.client = client));
  }

  previousState(): void {
    window.history.back();
  }
}
