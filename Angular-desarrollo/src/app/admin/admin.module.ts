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
  import { AnadirModalComponent } from '../admin/componentes/administrar-categoria/anadir-categoria/anadir-modal.component';
  import { TarjetaParticipantesComponent } from './componentes/tarjeta-participantes/tarjeta-participantes.component';
  import { AnadirComponent } from './componentes/administrar-subcategorias/anadir/anadir.component';
  import { AnadirMarcaComponent } from './componentes/administrar-marcas/anadir-marca/anadir.component';
  import { ModifSubcategoriaComponent } from './componentes/administrar-subcategorias/modif-subcategoria/modif-subcategoria.component';
  import { ModificarCategoriaComponent } from './componentes/administrar-categoria/modificar-categoria/modificar-categoria.component';
  import { EliminarCategoriaComponent } from './componentes/administrar-categoria/eliminar-categoria/eliminar-categoria.component';
  import { ModificarMarcaComponent } from './componentes/administrar-marcas/modificar-marca/modificar-marca.component';
  import { EliminarMarcaComponent } from './componentes/administrar-marcas/eliminar-marca/eliminar-marca.component';
  import { EliminarSubcategoriaComponent } from './componentes/administrar-subcategorias/eliminar-subcategoria/eliminar-subcategoria.component';
  import { AdministrarTiposComponent } from './componentes/administrar-tipos/administrar-tipos.component';
  import { AdministrarPresentacionesComponent } from './componentes/administrar-presentaciones/administrar-presentaciones.component';
  import { AnadirUsuarioComponent } from './componentes/administrar-usuarios/anadir-usuario/anadir-usuario.component';
  import { ModificarUsuarioComponent } from './componentes/administrar-usuarios/modificar-usuario/modificar-usuario.component';
  import { EliminarUsuarioComponent } from './componentes/administrar-usuarios/eliminar-usuario/eliminar-usuario.component';
  import { AnadirTipoComponent } from './componentes/administrar-tipos/anadir-tipo/anadir-tipo.component';
  import { ModificarTipoComponent } from './componentes/administrar-tipos/modificar-tipo/modificar-tipo.component';
  import { EliminarTipoComponent } from './componentes/administrar-tipos/eliminar-tipo/eliminar-tipo.component';
  import { AnadirPresentacionComponent } from './componentes/administrar-presentaciones/anadir-presentacion/anadir-presentacion.component';
  import { ModificarPresentacionComponent } from './componentes/administrar-presentaciones/modificar-presentacion/modificar-presentacion.component';
  import { EliminarPresentacionComponent } from './componentes/administrar-presentaciones/eliminar-presentacion/eliminar-presentacion.component';
    

  //Servicios
  import { SolicitudEstudioService } from "./Servicios/solicitud-estudio.service";
  import { PreguntaService } from "./Servicios/pregunta.service";
  import {  AdminMarcasService } from "./Servicios/administrar-marcas/admin-marcas.service";
  import {  AdministrarCategoriasService } from "./Servicios/administrar-categorias/administrar-categorias.service";
  import {  AdministrarSubcategoriasService } from "./Servicios/administrar-subcategorias/administrar-subcategorias.service";
  import {  AdministrarTiposService } from "./Servicios/administrar-tipos/administrar-tipos.service";
  import {  AdministrarPresentacionService } from "./Servicios/administrar-presentacion/administrar-presentacion.service";

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
      AnadirModalComponent,
      TarjetaParticipantesComponent,
      AnadirComponent,
      AnadirMarcaComponent,
      ModifSubcategoriaComponent,
      ModificarCategoriaComponent,
      EliminarCategoriaComponent,
      ModificarMarcaComponent,
      EliminarMarcaComponent,
      EliminarSubcategoriaComponent,
      AdministrarTiposComponent,
      AdministrarPresentacionesComponent,
      AnadirUsuarioComponent,
      ModificarUsuarioComponent,
      EliminarUsuarioComponent,
      AnadirTipoComponent,
      ModificarTipoComponent,
      EliminarTipoComponent,
      AnadirPresentacionComponent,
      ModificarPresentacionComponent,
      EliminarPresentacionComponent
    ],
    imports: [
      CommonModule,
      AdminRoutingModule,
      ComunModule,
      MaterialModule,
      ReactiveFormsModule,
      FormsModule
    ],
    providers:[
      SolicitudEstudioService,
      PreguntaService,  
      AdminMarcasService, 
      AdministrarCategoriasService,
      AdministrarSubcategoriasService,
      AdministrarTiposService,
      AdministrarPresentacionService
    ]
  })
  export class AdminModule { }

