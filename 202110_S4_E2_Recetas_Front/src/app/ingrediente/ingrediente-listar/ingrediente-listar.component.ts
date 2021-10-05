import { Component, OnInit } from '@angular/core';
import { Ingrediente } from '../ingrediente';
import { IngredienteService } from '../ingrediente.service';

@Component({
  selector: 'app-ingrediente-listar',
  templateUrl: './ingrediente-listar.component.html',
  styleUrls: ['./ingrediente-listar.component.css']
})
export class IngredienteListarComponent implements OnInit {

  constructor(private ingredienteService: IngredienteService) { }
  ingredientes: Array<Ingrediente> = [];

  getIngredientes():void
  {
    this.ingredienteService.getIngredientes().subscribe(ingredientes => {this.ingredientes = ingredientes;});
  }

  ngOnInit() {
    this.getIngredientes();
  }

}
