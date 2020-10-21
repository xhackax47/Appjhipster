import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModuleCyber06 } from 'app/shared/model/module-cyber-06.model';

@Component({
  selector: 'jhi-module-cyber-06-detail',
  templateUrl: './module-cyber-06-detail.component.html',
})
export class ModuleCyber06DetailComponent implements OnInit {
  module: IModuleCyber06 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ module }) => (this.module = module));
  }

  previousState(): void {
    window.history.back();
  }
}
