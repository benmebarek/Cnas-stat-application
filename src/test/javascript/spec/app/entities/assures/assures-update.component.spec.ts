import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CnasStatTestModule } from '../../../test.module';
import { AssuresUpdateComponent } from 'app/entities/assures/assures-update.component';
import { AssuresService } from 'app/entities/assures/assures.service';
import { Assures } from 'app/shared/model/assures.model';

describe('Component Tests', () => {
  describe('Assures Management Update Component', () => {
    let comp: AssuresUpdateComponent;
    let fixture: ComponentFixture<AssuresUpdateComponent>;
    let service: AssuresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CnasStatTestModule],
        declarations: [AssuresUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssuresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssuresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssuresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Assures(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Assures();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
