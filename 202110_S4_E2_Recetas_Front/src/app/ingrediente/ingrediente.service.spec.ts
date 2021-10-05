/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { IngredienteService } from './ingrediente.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { Ingrediente } from './ingrediente';

 describe("Service: Ingrediente", () => {
  let injector: TestBed;
  let service: IngredienteService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "usuarios/100/recetas/100/ingredientes";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [IngredienteService],
    });
    injector = getTestBed();
    service = injector.get(IngredienteService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Ingrediente[] = [];

    for (let i = 1; i < 11; i++) {
      let ingrediente = new Ingrediente(
        i,
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        null
      );

      mockPosts.push(ingrediente);
    }

    service.getIngredientes().subscribe((ingredientes) => {
      expect(ingredientes.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });

