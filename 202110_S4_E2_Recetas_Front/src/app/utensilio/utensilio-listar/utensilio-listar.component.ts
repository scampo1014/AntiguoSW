import { Component, OnInit } from '@angular/core';
import { Utensilio } from '../utensilio';
import { UtensilioService } from '../utensilio.service';

@Component({
  selector: 'app-utensilio-listar',
  templateUrl: './utensilio-listar.component.html',
  styleUrls: ['./utensilio-listar.component.css']
})
export class UtensilioListarComponent implements OnInit {

  constructor(private utensilioService: UtensilioService) { }
  utensilios: Array<Utensilio> = [];
  getUtesilios(): void {
    this.utensilioService.getUtensilios().subscribe(utensilios =>{this.utensilios = utensilios;});
  }

  ngOnInit() {
    this.getUtesilios();
  }

}
