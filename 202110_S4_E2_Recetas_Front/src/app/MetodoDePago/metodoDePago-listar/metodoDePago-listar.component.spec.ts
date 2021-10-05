import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { MetodoDePagoListarComponent } from './metodoDePago-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MetodoDePago } from '../metodoDePago';


describe('MetodoDePagoListarComponent', () => {
  let component: MetodoDePagoListarComponent;
  let fixture: ComponentFixture<MetodoDePagoListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MetodoDePagoListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MetodoDePagoListarComponent);
    component = fixture.componentInstance;
    component.MetodosDePago = [
      new MetodoDePago(
        faker.random.number(),
        faker.lorem.word(),
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
