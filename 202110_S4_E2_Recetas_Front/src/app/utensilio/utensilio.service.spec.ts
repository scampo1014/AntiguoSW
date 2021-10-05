import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { UtensilioService } from './utensilio.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { Utensilio } from './utensilio';

 describe("Service: Utensilio", () => {
  let injector: TestBed;
  let service: UtensilioService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "usuarios/101/recetas/103/utensilios";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UtensilioService],
    });
    injector = getTestBed();
    service = injector.get(UtensilioService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Utensilio[] = [];

    for (let i = 1; i < 11; i++) {
      let utensilio = new Utensilio(
        i,
        faker.lorem.sentence(),
        faker.random.number(),
        faker.lorem.sentence(),
        null
      );

      mockPosts.push(utensilio);
    }

    service.getUtensilios().subscribe((utensilios) => {
      expect(utensilios.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });


