/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { FotoService } from './foto.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

 import faker from "faker";
 import { Foto } from "./foto";
 import { environment } from "../../environments/environment";


describe('Service: Foto', () => {
  let injector: TestBed;
  let service: FotoService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "fotos";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FotoService],
    });
    injector = getTestBed();
    service = injector.get(FotoService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  })

  it("getPost() should return 10 records", () => {
    let mockPosts: Foto[] = [];

    for (let i = 1; i < 11; i++) {
      let foto = new Foto(
        i,
        faker.image.imageUrl(),
        faker.lorem.sentence(),
        faker.random.number(),
        null
      );

      mockPosts.push(foto);
    }

    service.getFotos().subscribe((fotos) => {
      expect(fotos.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });

  it('should ...', inject([FotoService], (service: FotoService) => {
    expect(service).toBeTruthy();
  }));
});
