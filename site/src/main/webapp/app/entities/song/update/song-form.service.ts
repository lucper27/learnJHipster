import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISong, NewSong } from '../song.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISong for edit and NewSongFormGroupInput for create.
 */
type SongFormGroupInput = ISong | PartialWithRequiredKeyOf<NewSong>;

type SongFormDefaults = Pick<NewSong, 'id'>;

type SongFormGroupContent = {
  id: FormControl<ISong['id'] | NewSong['id']>;
  title: FormControl<ISong['title']>;
  duration: FormControl<ISong['duration']>;
  inclusionDate: FormControl<ISong['inclusionDate']>;
  album: FormControl<ISong['album']>;
};

export type SongFormGroup = FormGroup<SongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SongFormService {
  createSongFormGroup(song: SongFormGroupInput = { id: null }): SongFormGroup {
    const songRawValue = {
      ...this.getFormDefaults(),
      ...song,
    };
    return new FormGroup<SongFormGroupContent>({
      id: new FormControl(
        { value: songRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(songRawValue.title, {
        validators: [Validators.required],
      }),
      duration: new FormControl(songRawValue.duration, {
        validators: [Validators.required, Validators.min(0)],
      }),
      inclusionDate: new FormControl(songRawValue.inclusionDate, {
        validators: [Validators.required],
      }),
      album: new FormControl(songRawValue.album, {
        validators: [Validators.required],
      }),
    });
  }

  getSong(form: SongFormGroup): ISong | NewSong {
    return form.getRawValue() as ISong | NewSong;
  }

  resetForm(form: SongFormGroup, song: SongFormGroupInput): void {
    const songRawValue = { ...this.getFormDefaults(), ...song };
    form.reset(
      {
        ...songRawValue,
        id: { value: songRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SongFormDefaults {
    return {
      id: null,
    };
  }
}
