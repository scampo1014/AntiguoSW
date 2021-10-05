import { Component, OnInit } from '@angular/core';
import { Foto } from '../foto';
import { FotoService } from '../foto.service';

@Component({
  selector: 'app-foto-listar',
  templateUrl: './foto-listar.component.html',
  styleUrls: ['./foto-listar.component.css']
})
export class FotoListarComponent implements OnInit {

  constructor(private fotoService: FotoService) { }
  fotos: Array<Foto>;

  getFotos(): void {
    this.fotoService.getFotos()
      .subscribe(fotos => {
        this.fotos = fotos;
      });
  }

  ngOnInit() {
    this.getFotos();
  }

}
