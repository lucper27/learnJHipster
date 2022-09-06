import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../style.test-samples';

import { StyleFormService } from './style-form.service';

describe('Style Form Service', () => {
  let service: StyleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StyleFormService);
  });

  describe('Service methods', () => {
    describe('createStyleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStyleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IStyle should create a new form with FormGroup', () => {
        const formGroup = service.createStyleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getStyle', () => {
      it('should return NewStyle for default Style initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStyleFormGroup(sampleWithNewData);

        const style = service.getStyle(formGroup) as any;

        expect(style).toMatchObject(sampleWithNewData);
      });

      it('should return NewStyle for empty Style initial value', () => {
        const formGroup = service.createStyleFormGroup();

        const style = service.getStyle(formGroup) as any;

        expect(style).toMatchObject({});
      });

      it('should return IStyle', () => {
        const formGroup = service.createStyleFormGroup(sampleWithRequiredData);

        const style = service.getStyle(formGroup) as any;

        expect(style).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStyle should not enable id FormControl', () => {
        const formGroup = service.createStyleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStyle should disable id FormControl', () => {
        const formGroup = service.createStyleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
