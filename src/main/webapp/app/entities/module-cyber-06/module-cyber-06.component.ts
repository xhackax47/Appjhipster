import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModuleCyber06 } from 'app/shared/model/module-cyber-06.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ModuleCyber06Service } from './module-cyber-06.service';
import { ModuleCyber06DeleteDialogComponent } from './module-cyber-06-delete-dialog.component';

@Component({
  selector: 'jhi-module-cyber-06',
  templateUrl: './module-cyber-06.component.html',
})
export class ModuleCyber06Component implements OnInit, OnDestroy {
  modules: IModuleCyber06[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected moduleService: ModuleCyber06Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.modules = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.moduleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IModuleCyber06[]>) => this.paginateModules(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.modules = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModuleCyber06): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModules(): void {
    this.eventSubscriber = this.eventManager.subscribe('moduleListModification', () => this.reset());
  }

  delete(module: IModuleCyber06): void {
    const modalRef = this.modalService.open(ModuleCyber06DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.module = module;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateModules(data: IModuleCyber06[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.modules.push(data[i]);
      }
    }
  }
}
