import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import faker from 'faker';
import { AnuncioListarComponent } from './anuncio-listar.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Anuncio } from '../anuncio';


describe('AnuncioListarComponent', () => {
  let component: AnuncioListarComponent;
  let fixture: ComponentFixture<AnuncioListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnuncioListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnuncioListarComponent);
    component = fixture.componentInstance;
    component.Anuncios = [
      new Anuncio(
        faker.random.number(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.random.number(),
        null
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
