import { Component, OnInit } from '@angular/core';
import { MetodoDePago } from '../metodoDePago';
import { MetodoDePagoService } from '../metodoDePago.service';

@Component({
  selector: 'app-metodoDePago-listar',
  templateUrl: './metodoDePago-listar.component.html',
  styleUrls: ['./metodoDePago-listar.component.css']
})

export class MetodoDePagoListarComponent implements OnInit {
  constructor(private MetodoDePagoService: MetodoDePagoService) { }
  MetodosDePago: Array<MetodoDePago> = [];

  getMetodoDePago():void{
    this.MetodoDePagoService.getMetodosDePago().subscribe(MetodosDePago => {this.MetodosDePago = MetodosDePago;});
  }

  ngOnInit() {
    this.getMetodoDePago();
  }

}
