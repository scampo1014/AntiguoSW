/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { VideoListarComponent } from './video-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Video } from "../video";

describe('VideoListarComponent', () => {
  let component: VideoListarComponent;
  let fixture: ComponentFixture<VideoListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VideoListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoListarComponent);
    component = fixture.componentInstance;
    component.videos = [
      new Video(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.image.imageUrl(),
        faker.random.number(),
        faker.random.number(),
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
