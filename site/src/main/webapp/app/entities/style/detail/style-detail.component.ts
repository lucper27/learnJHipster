import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStyle } from '../style.model';

@Component({
  selector: 'jhi-style-detail',
  templateUrl: './style-detail.component.html',
})
export class StyleDetailComponent implements OnInit {
  style: IStyle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ style }) => {
      this.style = style;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
