import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//Componentes
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { AdministrarEstudiosComponent } from "./componentes/administrar-estudios/administrar-estudios.component";
import {AsignarEncuestaComponent} from "./componentes/asignar-encuesta/asignar-encuesta.component"
import { AdministrarEstudiosProgresoComponent } from "./componentes/administrar-estudios-progreso/administrar-estudios-progreso.component";
import { EstudioDetalleComponent } from "./componentes/estudio-detalle/estudio-detalle.component";
import {AdministrarMarcasComponent} from "./componentes/administrar-marcas/administrar-marcas.component";
import {AdministrarCategoriaComponent} from "./componentes/administrar-categoria/administrar-categoria.component";
import {AdministrarSubcategoriasComponent} from "./componentes/administrar-subcategorias/administrar-subcategorias.component";
import {AdministrarUsuariosComponent} from "./componentes/administrar-usuarios/administrar-usuarios.component";
import { AdministrarTiposComponent } from './componentes/administrar-tipos/administrar-tipos.component';
import { AdministrarPresentacionesComponent } from './componentes/administrar-presentaciones/administrar-presentaciones.component';

const routes: Routes = [
  {path:'', component: DashboardComponent},
  {path:'administrar-estudio', component: DashboardComponent},
  {path:'administrar-estudios', component: AdministrarEstudiosComponent},
  {path:'administrar-estudios-progreso', component: AdministrarEstudiosProgresoComponent},
  { path: 'AsignarEncuesta/:id',     component:AsignarEncuestaComponent  },
  { path: 'EstudioDetalle/:id',     component:EstudioDetalleComponent  } ,
  { path: 'administrar-marcas',   component: AdministrarMarcasComponent  },
  { path: 'administrar-categoria',   component: AdministrarCategoriaComponent  },
  { path: 'administrar-subcategorias',   component: AdministrarSubcategoriasComponent},
  { path: 'administrar-usuarios',   component: AdministrarUsuariosComponent  },
  { path: 'administrar-tipos',   component: AdministrarTiposComponent  },
  { path: 'administrar-presentaciones',   component: AdministrarPresentacionesComponent  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
