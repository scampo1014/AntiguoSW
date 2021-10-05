import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpErrorInterceptor } from './interceptors/http-error-interceptor.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FotoModule } from './foto/foto.module';
import { ComentarioModule} from './comentario/comentario.module';
import { VideoModule } from './video/video.module';

import { AnuncioModule } from './Anuncio/anuncio.module'
import { ProveedorModule } from './Proveedor/proveedor.module';
import { MetodoDePagoModule } from './MetodoDePago/metodoDePago.module';

import { IngredienteModule } from './ingrediente/ingrediente.module';
import { UsuarioModule } from './usuario/usuario.module';
import { UtensilioModule } from './utensilio/utensilio.module';

import { RecetaModule } from './receta/receta.module';
import { CalificacionModule } from './calificacion/calificacion.module';

import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FotoModule,
    VideoModule,
    ComentarioModule,
    IngredienteModule,
    UsuarioModule,
    UtensilioModule,
    ProveedorModule,
    AnuncioModule,
    MetodoDePagoModule,
    RecetaModule,
    CalificacionModule,
    AppRoutingModule,
    HttpClientModule,
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }),
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
