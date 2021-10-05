import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { IngredienteListarComponent } from './ingrediente-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Ingrediente } from '../ingrediente';


describe('IngredienteListarComponent', () => {
  let component: IngredienteListarComponent;
  let fixture: ComponentFixture<IngredienteListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IngredienteListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IngredienteListarComponent);
    component = fixture.componentInstance;
    component.ingredientes = [
      new Ingrediente(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number()
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
