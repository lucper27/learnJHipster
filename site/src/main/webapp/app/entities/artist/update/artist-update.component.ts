import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ArtistFormService, ArtistFormGroup } from './artist-form.service';
import { IArtist } from '../artist.model';
import { ArtistService } from '../service/artist.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-artist-update',
  templateUrl: './artist-update.component.html',
})
export class ArtistUpdateComponent implements OnInit {
  isSaving = false;
  artist: IArtist | null = null;

  editForm: ArtistFormGroup = this.artistFormService.createArtistFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected artistService: ArtistService,
    protected artistFormService: ArtistFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artist }) => {
      this.artist = artist;
      if (artist) {
        this.updateForm(artist);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('learnjhipsterApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const artist = this.artistFormService.getArtist(this.editForm);
    if (artist.id !== null) {
      this.subscribeToSaveResponse(this.artistService.update(artist));
    } else {
      this.subscribeToSaveResponse(this.artistService.create(artist));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtist>>): void {
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

  protected updateForm(artist: IArtist): void {
    this.artist = artist;
    this.artistFormService.resetForm(this.editForm, artist);
  }
}
