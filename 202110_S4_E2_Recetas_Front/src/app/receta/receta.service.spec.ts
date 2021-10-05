/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { RecetaService } from "./receta.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { Receta } from "./receta";
import { RecetaDetail} from "./recetaDetail";
import { environment } from "../../environments/environment";

describe("Service: Receta", () => {
 let injector: TestBed;
 let service: RecetaService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "recetas";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [RecetaService],
   });
   injector = getTestBed();
   service = injector.get(RecetaService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 10 records", () => {
   let mockPosts: Receta[] = [];

   for (let i = 1; i < 11; i++) {
     let receta = new Receta(
       i,
       faker.lorem.sentence(),
       faker.random.number(),
       faker.lorem.sentence(),
       faker.random.number(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       true,
       null
     );

     mockPosts.push(receta);
   }

   service.getRecetas().subscribe((recetas) => {
     expect(recetas.length).toBe(10);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
