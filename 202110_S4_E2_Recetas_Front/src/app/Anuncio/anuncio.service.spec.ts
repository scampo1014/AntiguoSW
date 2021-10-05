/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { AnuncioService } from './anuncio.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { Anuncio } from './anuncio';

 describe("Service: anuncio", () => {
  let injector: TestBed;
  let service: AnuncioService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "proveedores/100/anuncios";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AnuncioService],
    });
    injector = getTestBed();
    service = injector.get(AnuncioService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Anuncio[] = [];

    for (let i = 1; i < 11; i++) {
      let anuncio = new Anuncio(
        i,
        faker.lorem.sentence(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.random.number(),
        null
      );

      mockPosts.push(anuncio);
    }

    service.getAnuncios().subscribe((anuncios) => {
      expect(anuncios.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });
