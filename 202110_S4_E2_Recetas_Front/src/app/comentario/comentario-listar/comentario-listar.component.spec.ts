/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ComentarioListarComponent } from './comentario-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Comentario } from "../comentario";
import { Receta } from "src/app/receta/receta";
import { Usuario } from "src/app/usuario/usuario";
import { Proveedor } from "src/app/Proveedor/proveedor";

describe('ComentarioListarComponent', () => {
  let component: ComentarioListarComponent;
  let fixture: ComponentFixture<ComentarioListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComentarioListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComentarioListarComponent);
    component = fixture.componentInstance;

    //Crear Fake Receta y Usuario aquí
    component.comentarios = [
      new Comentario(
        faker.random.number(),
        true,
        faker.lorem.sentence(),
        true,
        null,
        null,
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
