import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStyle } from '../style.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../style.test-samples';

import { StyleService } from './style.service';

const requireRestSample: IStyle = {
  ...sampleWithRequiredData,
};

describe('Style Service', () => {
  let service: StyleService;
  let httpMock: HttpTestingController;
  let expectedResult: IStyle | IStyle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StyleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Style', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const style = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(style).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Style', () => {
      const style = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(style).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Style', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Style', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Style', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStyleToCollectionIfMissing', () => {
      it('should add a Style to an empty array', () => {
        const style: IStyle = sampleWithRequiredData;
        expectedResult = service.addStyleToCollectionIfMissing([], style);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(style);
      });

      it('should not add a Style to an array that contains it', () => {
        const style: IStyle = sampleWithRequiredData;
        const styleCollection: IStyle[] = [
          {
            ...style,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStyleToCollectionIfMissing(styleCollection, style);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Style to an array that doesn't contain it", () => {
        const style: IStyle = sampleWithRequiredData;
        const styleCollection: IStyle[] = [sampleWithPartialData];
        expectedResult = service.addStyleToCollectionIfMissing(styleCollection, style);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(style);
      });

      it('should add only unique Style to an array', () => {
        const styleArray: IStyle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const styleCollection: IStyle[] = [sampleWithRequiredData];
        expectedResult = service.addStyleToCollectionIfMissing(styleCollection, ...styleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const style: IStyle = sampleWithRequiredData;
        const style2: IStyle = sampleWithPartialData;
        expectedResult = service.addStyleToCollectionIfMissing([], style, style2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(style);
        expect(expectedResult).toContain(style2);
      });

      it('should accept null and undefined values', () => {
        const style: IStyle = sampleWithRequiredData;
        expectedResult = service.addStyleToCollectionIfMissing([], null, style, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(style);
      });

      it('should return initial array if no Style is added', () => {
        const styleCollection: IStyle[] = [sampleWithRequiredData];
        expectedResult = service.addStyleToCollectionIfMissing(styleCollection, undefined, null);
        expect(expectedResult).toEqual(styleCollection);
      });
    });

    describe('compareStyle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStyle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStyle(entity1, entity2);
        const compareResult2 = service.compareStyle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStyle(entity1, entity2);
        const compareResult2 = service.compareStyle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStyle(entity1, entity2);
        const compareResult2 = service.compareStyle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
