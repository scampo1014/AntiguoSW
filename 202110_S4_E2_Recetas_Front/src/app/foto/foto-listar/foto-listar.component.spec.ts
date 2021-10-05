/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { FotoListarComponent } from "./foto-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Foto } from "../foto";

describe("FotoListarComponent", () => {
 let component: FotoListarComponent;
 let fixture: ComponentFixture<FotoListarComponent>;
 let debug: DebugElement;

 beforeEach(async(() => {
   TestBed.configureTestingModule({
     declarations: [FotoListarComponent],
     imports: [HttpClientTestingModule],
   }).compileComponents();
 }));

 beforeEach(() => {
   fixture = TestBed.createComponent(FotoListarComponent);
   component = fixture.componentInstance;
   component.fotos = [
     new Foto(
      faker.random.number(),
      faker.image.imageUrl(),
      faker.lorem.sentence(),
      faker.random.number(),
     ),
   ];
   fixture.detectChanges();
   debug = fixture.debugElement;
 });

 it("should create", () => {
   expect(component).toBeTruthy();
 });

});
