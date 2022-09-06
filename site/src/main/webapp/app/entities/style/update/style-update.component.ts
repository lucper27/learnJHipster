import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StyleFormService, StyleFormGroup } from './style-form.service';
import { IStyle } from '../style.model';
import { StyleService } from '../service/style.service';

@Component({
  selector: 'jhi-style-update',
  templateUrl: './style-update.component.html',
})
export class StyleUpdateComponent implements OnInit {
  isSaving = false;
  style: IStyle | null = null;

  editForm: StyleFormGroup = this.styleFormService.createStyleFormGroup();

  constructor(
    protected styleService: StyleService,
    protected styleFormService: StyleFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ style }) => {
      this.style = style;
      if (style) {
        this.updateForm(style);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const style = this.styleFormService.getStyle(this.editForm);
    if (style.id !== null) {
      this.subscribeToSaveResponse(this.styleService.update(style));
    } else {
      this.subscribeToSaveResponse(this.styleService.create(style));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStyle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(style: IStyle): void {
    this.style = style;
    this.styleFormService.resetForm(this.editForm, style);
  }
}
