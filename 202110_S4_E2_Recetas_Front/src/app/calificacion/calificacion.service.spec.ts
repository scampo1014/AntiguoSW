/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { CalificacionService } from "./calificacion.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { Calificacion } from "./calificacion";
import { environment } from "../../environments/environment";

describe("Service: Calificacion", () => {
 let injector: TestBed;
 let service: CalificacionService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "calificaciones";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [CalificacionService],
   });
   injector = getTestBed();
   service = injector.get(CalificacionService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 10 records", () => {
   let mockPosts: Calificacion[] = [];

   for (let i = 1; i < 11; i++) {
     let calificacion = new Calificacion(
       i,
       faker.lorem.sentence(),
       faker.random.number(),
       null,
       null,
       null
     );

     mockPosts.push(calificacion);
   }

   service.getCalificaciones().subscribe((calificacions) => {
     expect(calificacions.length).toBe(10);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
