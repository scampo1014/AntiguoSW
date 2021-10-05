/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { MetodoDePagoService } from './metodoDePago.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { MetodoDePago } from './metodoDePago';

 describe("Service: metodoDePago", () => {
  let injector: TestBed;
  let service: MetodoDePagoService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "proveedores/100/metodosPago";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MetodoDePagoService],
    });
    injector = getTestBed();
    service = injector.get(MetodoDePagoService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: MetodoDePago[] = [];

    for (let i = 1; i < 11; i++) {
      let metodoDePago = new MetodoDePago(
        faker.lorem.sentence(),
        faker.lorem.word(),
      );

      mockPosts.push(metodoDePago);
    }

    service.getMetodosDePago().subscribe((MetodosDePago) => {
      expect(MetodosDePago.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });
