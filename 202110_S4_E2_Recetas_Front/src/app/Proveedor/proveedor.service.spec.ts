/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { ProveedorService } from './proveedor.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { Proveedor } from './proveedor';

 describe("Service: proveedor", () => {
  let injector: TestBed;
  let service: ProveedorService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "proveedores";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProveedorService],
    });
    injector = getTestBed();
    service = injector.get(ProveedorService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Proveedor[] = [];

    for (let i = 1; i < 11; i++) {
      let proveedor = new Proveedor(
        faker.lorem.sentence(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
      );

      mockPosts.push(proveedor);
    }

    service.getProveedores().subscribe((proveedores) => {
      expect(proveedores.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });
