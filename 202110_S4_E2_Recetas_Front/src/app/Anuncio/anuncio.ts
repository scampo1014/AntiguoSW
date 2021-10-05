import { Proveedor } from '../Proveedor/proveedor';
export class Anuncio {
  id:number;
  nombre: string;
  costo: string;
  contenido: string;
  medioDePago: string;
  tiempoDisponible :number;
  proveedor: Proveedor;

  constructor(
    id:number,
  nombre:string,
  costo: string,
  contenido: string,
  medioDePago: string,
  tiempoDisponible:number,
  proveedor: Proveedor
  )
  {
    this.id=id;
    this.nombre = nombre;
    this.costo = costo;
    this.contenido = contenido;
    this.medioDePago = medioDePago;
    this.tiempoDisponible=tiempoDisponible;
    this.proveedor=proveedor;
  }
}
