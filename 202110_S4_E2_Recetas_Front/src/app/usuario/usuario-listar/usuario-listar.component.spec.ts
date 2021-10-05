import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { UsuarioListarComponent } from './usuario-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Usuario } from '../usuario';
import { UsuarioDetail } from '../usuarioDetail';


describe('UsuarioListarComponent', () => {
  let component: UsuarioListarComponent;
  let fixture: ComponentFixture<UsuarioListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsuarioListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioListarComponent);
    component = fixture.componentInstance;
    component.usuarios = [
      new UsuarioDetail(
        faker.random.number(),
        faker.random.word(),
        faker.random.word(),
        faker.random.word(),
        faker.random.word(),
        faker.random.word(),
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
