import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Receta } from '../receta';
import { RecetaService } from '../receta.service';
import { ToastrService } from 'ngx-toastr';
import { Foto } from 'src/app/foto/foto';

@Component({
  selector: 'app-receta-foto-create',
  templateUrl: './receta-foto-create.component.html',
  styleUrls: ['./receta-foto-create.component.css']
})
export class RecetaFotoCreateComponent implements OnInit {

  public isCollapsed =true;
  fotoForm: FormGroup;

  @Input() receta: Receta;

  constructor(
    private recetaService:RecetaService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.fotoForm = this.formBuilder.group({
      foto: ['', [Validators.required, Validators.minLength(2)]],
  })
}
createFoto(newFoto: Foto) {
  newFoto.receta = this.receta;
  this.recetaService.createFoto(this.receta.id, newFoto)
    .subscribe(r => {
      this.toastrService.success('La foto fue creada');
      this.fotoForm.reset();
    },err => {
      this.toastrService.error(err, 'Error');
    })
  // Process checkout data here
  console.warn("La foto fue creada", newFoto);
}

cancelCreation() {
  console.log("Cancelando ...");
  this.toastrService.warning('La foto no fue creada', 'Review creation');
  this.fotoForm.reset();
}

}
