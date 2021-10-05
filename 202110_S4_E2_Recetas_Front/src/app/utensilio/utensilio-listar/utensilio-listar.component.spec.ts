import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { UtensilioListarComponent } from './utensilio-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Utensilio } from '../utensilio';


describe('UtensilioListarComponent', () => {
  let component: UtensilioListarComponent;
  let fixture: ComponentFixture<UtensilioListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UtensilioListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UtensilioListarComponent);
    component = fixture.componentInstance;
    component.utensilios = [
      new Utensilio(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.lorem.sentence()
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
