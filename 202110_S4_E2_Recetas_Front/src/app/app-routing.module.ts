import { NgModule } from '@angular/core';
import {CommonModule} from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CalificacionListarComponent} from './calificacion/calificacion-listar/calificacion-listar.component';
import { ComentarioListarComponent} from './comentario/comentario-listar/comentario-listar.component';
import { FotoListarComponent} from './foto/foto-listar/foto-listar.component';
import { ProveedorListarComponent} from './Proveedor/proveedor-listar/proveedor-listar.component';
import { RecetaListarComponent} from './receta/receta-listar/receta-listar.component';
import { UsuarioListarComponent} from './usuario/usuario-listar/usuario-listar.component';
import { VideoListarComponent} from './video/video-listar/video-listar.component';
import { ComentarioDetailComponent } from './comentario/comentario-detail/comentario-detail.component';
import { ProveedorDetailComponent } from './Proveedor/proveedor-detail/proveedor-detail.component';
import { RecetaDetailComponent } from './receta/receta-detail/receta-detail.component';
import { UsuarioDetailComponent } from './usuario/usuario-detail/usuario-detail.component';
import { CalificacionUsuarioCreateComponent } from './usuario/calificacion-usuario-create/calificacion-usuario-create.component';
import { UsuarioCreateComponent } from './usuario/usuario-create/usuario-create.component';

const routes: Routes = [
  {
    path: '',
    component: RecetaListarComponent
  },
  {
    path: 'calificaciones',
    children: [
      {
        path:'listar',
        component: CalificacionListarComponent
      },
       {
         path: 'create',
         component: CalificacionUsuarioCreateComponent
       }
    ]
  },
  {
    path: 'comentarios',
    children: [
      {
        path:'listar',
        component: ComentarioListarComponent
      },
      {
        path:':id',
        component: ComentarioDetailComponent
      }
    ]
  },
  {
    path: 'fotos',
    children: [
      {
        path:'listar',
        component: FotoListarComponent
      }
    ]
  },
  {
    path: 'proveedores',
    children: [
      {
        path:'listar',
        component: ProveedorListarComponent
      },
      {
        path:':id',
        component: ProveedorDetailComponent
      }
    ]
  },
  {
    path: 'recetas',
    children: [
      {
        path:'listar',
        component: RecetaListarComponent
      },
      {
        path:':id',
        component: RecetaDetailComponent
      }
    ]
  },
  {
    path: 'usuarios',
    children: [
      {
        path:'listar',
        component: UsuarioListarComponent
      },
      {
        path:'create',
        component: UsuarioCreateComponent
      },
      {
        path:':id',
        component: UsuarioDetailComponent
      }
    ]
  },
  {
    path: 'videos',
    children: [
      {
        path:'listar',
        component: VideoListarComponent
      }
    ]
  }



];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
