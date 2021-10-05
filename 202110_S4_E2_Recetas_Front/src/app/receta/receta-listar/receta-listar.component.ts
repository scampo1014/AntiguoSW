import { Component, OnInit } from '@angular/core';
import {RecetaDetail} from '../recetaDetail';
import {Receta} from '../receta';
import {RecetaService} from '../receta.service';

@Component({
  selector: 'app-receta-listar',
  templateUrl: './receta-listar.component.html',
  styleUrls: ['./receta-listar.component.css']
})
export class RecetaListarComponent implements OnInit {
  recetas: Array<RecetaDetail>=[];
  selectedReceta!: Receta;
  selected=false;
  constructor(private recetaService: RecetaService) { }
  getRecetas():void
  {
    this.recetaService.getRecetas().subscribe(recetas=>{this.recetas=recetas;})
  }
  ngOnInit() {
    this.getRecetas();
  }
  onSelected(r:Receta):void{
    this.selected=true;
    this.selectedReceta=r;
  }

}
