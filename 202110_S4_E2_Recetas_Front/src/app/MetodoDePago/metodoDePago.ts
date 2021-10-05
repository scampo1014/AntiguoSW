
export class MetodoDePago {
  id:number;
  metodoPago: string;

  constructor(
    id:number,
    metodoPago:string,
  )
  {
    this.id=id;
    this.metodoPago = metodoPago;
  }
}
