import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Receta } from '../receta';
import { RecetaService } from '../receta.service';
import { ToastrService } from 'ngx-toastr';
import { Utensilio } from 'src/app/utensilio/utensilio';

@Component({
  selector: 'app-utensilio-receta-create',
  templateUrl: './utensilio-receta-create.component.html',
  styleUrls: ['./utensilio-receta-create.component.css']
})
export class UtensilioRecetaCreateComponent implements OnInit {

  public isCollapsed =true;
  utensilioForm: FormGroup;

  @Input() receta: Receta;

  constructor(
    private recetaService:RecetaService,
    private toastrService: ToastrService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.utensilioForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ["", [Validators.required, Validators.minLength(5)]]
    })
  }
createUtensilio(newUtensilio: Utensilio) {
  newUtensilio.receta = this.receta;
  this.recetaService.createUtensilio(this.receta.id, newUtensilio)
    .subscribe(r => {
      this.toastrService.success('El utensilio fue creado');
      this.utensilioForm.reset();
    },err => {
      this.toastrService.error(err, 'Error');
    })
  // Process checkout data here
  console.warn("El utensilio fue creado", newUtensilio);
}

cancelCreation() {
  console.log("Cancelando ...");
  this.toastrService.warning('El utensilio no fue creado', 'Utensilio creation');
  this.utensilioForm.reset();
}

}
