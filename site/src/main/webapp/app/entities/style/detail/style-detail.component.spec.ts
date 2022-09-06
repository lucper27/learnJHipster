import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StyleDetailComponent } from './style-detail.component';

describe('Style Management Detail Component', () => {
  let comp: StyleDetailComponent;
  let fixture: ComponentFixture<StyleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StyleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ style: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StyleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StyleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load style on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.style).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
