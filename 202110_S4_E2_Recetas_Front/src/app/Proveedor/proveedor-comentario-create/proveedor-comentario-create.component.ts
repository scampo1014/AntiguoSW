import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Comentario } from '../../comentario/comentario';
import { Proveedor } from '../proveedor';
import { ProveedorService } from '../proveedor.service';

@Component({
  selector: 'app-proveedor-comentario-create',
  templateUrl: './proveedor-comentario-create.component.html',
  styleUrls: ['./proveedor-comentario-create.component.scss']
})
export class ProveedorComentarioCreateComponent implements OnInit {
  public isCollapsed = true;
  comentarioForm: FormGroup;

  @Input() proveedor: Proveedor;

  constructor(
    private proveedorService: ProveedorService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.comentarioForm = this.formBuilder.group({
      aprobado: [false, [Validators.required, Validators.requiredTrue]],
      positivo: [false, [Validators.required]],
      comentario: ['', [Validators.required, Validators.minLength(2)]]
    })
  }

  createComentario(newComentario: Comentario) {
    newComentario.proveedor = this.proveedor;
    this.proveedorService.createComentario(this.proveedor.id, newComentario)
      .subscribe(r => {
        this.toastrService.success('El comentario fue creado');
        this.comentarioForm.reset();
      }, err => {
        this.toastrService.error(err, 'Error');
      })

      console.warn("El comentario fue creado", newComentario);
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.toastrService.warning('El comentario no fue creado', 'comentario creation');
    this.comentarioForm.reset();
  }

}
