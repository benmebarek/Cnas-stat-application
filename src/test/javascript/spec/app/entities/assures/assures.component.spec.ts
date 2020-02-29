import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CnasStatTestModule } from '../../../test.module';
import { AssuresComponent } from 'app/entities/assures/assures.component';
import { AssuresService } from 'app/entities/assures/assures.service';
import { Assures } from 'app/shared/model/assures.model';

describe('Component Tests', () => {
  describe('Assures Management Component', () => {
    let comp: AssuresComponent;
    let fixture: ComponentFixture<AssuresComponent>;
    let service: AssuresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CnasStatTestModule],
        declarations: [AssuresComponent]
      })
        .overrideTemplate(AssuresComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssuresComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssuresService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Assures(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.assures && comp.assures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
