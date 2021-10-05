import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { ProveedorListarComponent } from './proveedor-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Proveedor } from '../proveedor';


describe('ProveedorListarComponent', () => {
  let component: ProveedorListarComponent;
  let fixture: ComponentFixture<ProveedorListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProveedorListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProveedorListarComponent);
    component = fixture.componentInstance;
    component.Proveedores = [
      new Proveedor(
        faker.random.number(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
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
