import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CnasStatTestModule } from '../../../test.module';
import { AssuresDetailComponent } from 'app/entities/assures/assures-detail.component';
import { Assures } from 'app/shared/model/assures.model';

describe('Component Tests', () => {
  describe('Assures Management Detail Component', () => {
    let comp: AssuresDetailComponent;
    let fixture: ComponentFixture<AssuresDetailComponent>;
    const route = ({ data: of({ assures: new Assures(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CnasStatTestModule],
        declarations: [AssuresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssuresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssuresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load assures on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assures).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
