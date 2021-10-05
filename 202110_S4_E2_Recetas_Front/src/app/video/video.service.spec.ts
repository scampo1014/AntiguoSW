/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { VideoService } from './video.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

import faker from "faker";
import { Video } from "./video";
import { environment } from "../../environments/environment";


describe('Service: Video', () => {
  let injector: TestBed;
  let service: VideoService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "videos";


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [VideoService],
    });
    injector = getTestBed();
    service = injector.get(VideoService);
    httpMock = injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getPost() should return 10 records", () => {
    let mockPosts: Video[] = [];

    for (let i = 1; i < 11; i++) {
      let video = new Video(
        i,
        faker.lorem.sentence(),
        faker.image.imageUrl(),
        faker.random.number(),
        faker.random.number(),
      );

      mockPosts.push(video);
    }

    service.getVideos().subscribe((videos) => {
      expect(videos.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });

  it('should ...', inject([VideoService], (service: VideoService) => {
    expect(service).toBeTruthy();
  }));
});
