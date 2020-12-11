  /* Modulos */

  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { ReactiveFormsModule } from '@angular/forms';
  import { AdminRoutingModule } from './admin-routing.module';
  import { ComunModule } from '../comun/comun.module';
  import { MaterialModule } from '.././material.module';
  import { FormsModule } from '@angular/forms';


  /* Componentes */
  import { AdminComponent } from './componentes/raiz/admin.component';
  import { DashboardComponent } from './componentes/dashboard/dashboard.component';
  import { AdministrarEstudiosComponent } from './componentes/administrar-estudios/administrar-estudios.component';
  import { TarjetaEstudiosComponent } from './componentes/tarjeta-estudios/tarjeta-estudios.component';
  import { AsignarEncuestaComponent } from './componentes/asignar-encuesta/asignar-encuesta.component';
  import { AdministrarEstudiosProgresoComponent } from './componentes/administrar-estudios-progreso/administrar-estudios-progreso.component';
  import { TarjetaEstudioProgresoComponent } from './componentes/tarjeta-estudio-progreso/tarjeta-estudio-progreso.component';
  import { EstudioDetalleComponent } from './componentes/estudio-detalle/estudio-detalle.component';
  import {AdministrarMarcasComponent} from "./componentes/administrar-marcas/administrar-marcas.component";
  import { ContenedorPreguntaNuevaComponent } from './componentes/Preguntas/contenedor-pregunta-nueva/contenedor-pregunta-nueva.component';
  import { ContenedorPreguntaViejaComponent } from './componentes/Preguntas/contenedor-pregunta-vieja/contenedor-pregunta-vieja.component';
  import { AdministrarCategoriaComponent } from './componentes/administrar-categoria/administrar-categoria.component';
  import { AdministrarSubcategoriasComponent } from './componentes/administrar-subcategorias/administrar-subcategorias.component';
  import { AdministrarUsuariosComponent } from './componentes/administrar-usuarios/administrar-usuarios.component';
  

  //Servicios
  import { SolicitudEstudioService } from "./Servicios/solicitud-estudio.service";
  import { PreguntaService } from "./Servicios/pregunta.service";
  import {  AdminMarcasService } from "./Servicios/administrar-marcas/admin-marcas.service";
  import {  AdministrarCategoriasService } from "./Servicios/administrar-categorias/administrar-categorias.service";
  import {  AdministrarSubcategoriasService } from "./Servicios/administrar-subcategorias/administrar-subcategorias.service";
  import { AnadirModalComponent } from '../admin/componentes/administrar-categoria/anadir/anadir-modal/anadir-modal.component';

  @NgModule({
    declarations: [
      AdminComponent,
      DashboardComponent,
      AdministrarEstudiosComponent,
      TarjetaEstudiosComponent,
      AsignarEncuestaComponent,
      AdministrarEstudiosProgresoComponent,
      TarjetaEstudioProgresoComponent,
      EstudioDetalleComponent,
      ContenedorPreguntaNuevaComponent,
      ContenedorPreguntaViejaComponent,
      AdministrarMarcasComponent,
      AdministrarCategoriaComponent,
      AdministrarSubcategoriasComponent,
      AdministrarUsuariosComponent,
      AnadirModalComponent
    ],
    imports: [
      CommonModule,
      AdminRoutingModule,
      ComunModule,
      MaterialModule,
      ReactiveFormsModule,
      FormsModule
    ],
    providers:[SolicitudEstudioService,
    PreguntaService,  AdminMarcasService, AdministrarCategoriasService,AdministrarSubcategoriasService
    ]
  })
  export class AdminModule { }
