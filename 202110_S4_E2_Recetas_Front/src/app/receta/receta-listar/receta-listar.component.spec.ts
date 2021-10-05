/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { RecetaListarComponent } from "./receta-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Receta } from "../receta";
import { RecetaDetail } from "../recetaDetail";

describe("RecetaListarComponent", () => {
 let component: RecetaListarComponent;
 let fixture: ComponentFixture<RecetaListarComponent>;
 let debug: DebugElement;

 beforeEach(async(() => {
   TestBed.configureTestingModule({
     declarations: [RecetaListarComponent],
     imports: [HttpClientTestingModule],
   }).compileComponents();
 }));

 beforeEach(() => {
   fixture = TestBed.createComponent(RecetaListarComponent);
   component = fixture.componentInstance;
   component.recetas = [
     new RecetaDetail(
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      true,
      null,
      // null,
      // null,
      // null,
      // null,
      // null,
     ),
   ];
   fixture.detectChanges();
   debug = fixture.debugElement;
 });

 it("should create", () => {
   expect(component).toBeTruthy();
 });

});
