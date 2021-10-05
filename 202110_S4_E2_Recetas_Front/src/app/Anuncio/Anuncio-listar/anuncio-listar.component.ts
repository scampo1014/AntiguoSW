import { Component, OnInit } from '@angular/core';
import { Anuncio } from '../anuncio';
import { AnuncioService } from '../anuncio.service';

@Component({
  selector: 'app-anuncio-listar',
  templateUrl: './anuncio-listar.component.html',
  styleUrls: ['./anuncio-listar.component.css']
})

export class AnuncioListarComponent implements OnInit {
  constructor(private AnuncioService: AnuncioService) { }
  Anuncios: Array<Anuncio> = [];

  selectedAnuncio!: Anuncio;
  selected = false;

  getAnuncio():void{
    this.AnuncioService.getAnuncios().subscribe(Anuncios => {this.Anuncios = Anuncios;});
  }

  onSelected(u: Anuncio): void {
    this.selected = true;
    this.selectedAnuncio = u;
  }

  ngOnInit() {
    this.getAnuncio();
  }

}
