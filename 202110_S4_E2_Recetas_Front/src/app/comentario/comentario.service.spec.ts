/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { ComentarioService } from './comentario.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

import faker from "faker";
import { Comentario } from "./comentario";
import { environment } from "../../environments/environment";


describe('Service: Comentario', () => {
  let injector: TestBed;
  let service: ComentarioService;
  let httpMock: HttpTestingController;
   let apiUrl = environment.baseUrl + "comentarios";


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ComentarioService],
    });
    injector = getTestBed();
    service = injector.get(ComentarioService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Comentario[] = [];

    for (let i = 1; i < 11; i++) {
      let comentario = new Comentario(
        i,
        true,
        faker.lorem.sentence(),
        true,
        null,
        null,
        //null,
      );

      mockPosts.push(comentario);
    }

    service.getComentarios().subscribe((comentarios) => {
      expect(comentarios.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });

  it('should ...', inject([ComentarioService], (service: ComentarioService) => {
    expect(service).toBeTruthy();
  }));
});
