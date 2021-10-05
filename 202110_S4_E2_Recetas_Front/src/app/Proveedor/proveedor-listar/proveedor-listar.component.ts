import { Component, OnInit } from '@angular/core';
import { Proveedor } from '../proveedor';
import { ProveedorDetail } from '../proveedorDetail';
import { ProveedorService } from '../proveedor.service';

@Component({
  selector: 'app-proveedor-listar',
  templateUrl: './proveedor-listar.component.html',
  styleUrls: ['./proveedor-listar.component.css']
})

export class ProveedorListarComponent implements OnInit {
  constructor(private ProveedorService: ProveedorService) { }
  Proveedores: Array<Proveedor> = [];


  selectedProveedor!: Proveedor;
  selected = false;

  getProveedor():void{
    this.ProveedorService.getProveedores().subscribe(Proveedores => {this.Proveedores = Proveedores;});
  }
  onSelected(u: Proveedor): void {
    this.selected = true;
    this.selectedProveedor = u;
  }

  ngOnInit() {
    this.getProveedor();
  }

}
