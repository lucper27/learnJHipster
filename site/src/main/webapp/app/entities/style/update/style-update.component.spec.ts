import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StyleFormService } from './style-form.service';
import { StyleService } from '../service/style.service';
import { IStyle } from '../style.model';

import { StyleUpdateComponent } from './style-update.component';

describe('Style Management Update Component', () => {
  let comp: StyleUpdateComponent;
  let fixture: ComponentFixture<StyleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let styleFormService: StyleFormService;
  let styleService: StyleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StyleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StyleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StyleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    styleFormService = TestBed.inject(StyleFormService);
    styleService = TestBed.inject(StyleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const style: IStyle = { id: 456 };

      activatedRoute.data = of({ style });
      comp.ngOnInit();

      expect(comp.style).toEqual(style);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStyle>>();
      const style = { id: 123 };
      jest.spyOn(styleFormService, 'getStyle').mockReturnValue(style);
      jest.spyOn(styleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ style });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: style }));
      saveSubject.complete();

      // THEN
      expect(styleFormService.getStyle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(styleService.update).toHaveBeenCalledWith(expect.objectContaining(style));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStyle>>();
      const style = { id: 123 };
      jest.spyOn(styleFormService, 'getStyle').mockReturnValue({ id: null });
      jest.spyOn(styleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ style: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: style }));
      saveSubject.complete();

      // THEN
      expect(styleFormService.getStyle).toHaveBeenCalled();
      expect(styleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStyle>>();
      const style = { id: 123 };
      jest.spyOn(styleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ style });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(styleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
