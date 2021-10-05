/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { UsuarioService } from './usuario.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { environment } from "../../environments/environment";
import { Usuario } from './usuario';

 describe("Service: Usuario", () => {
  let injector: TestBed;
  let service: UsuarioService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "usuarios";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsuarioService],
    });
    injector = getTestBed();
    service = injector.get(UsuarioService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Usuario[] = [];

    for (let i = 1; i < 11; i++) {
      let usuario = new Usuario(
        i,
        faker.lorem.word(),
        faker.random.word(),
        faker.lorem.word(),
        faker.lorem.word(),
        faker.lorem.word(),
      );

      mockPosts.push(usuario);
    }

    service.getUsuarios().subscribe((usuarios) => {
      expect(usuarios.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
 });
