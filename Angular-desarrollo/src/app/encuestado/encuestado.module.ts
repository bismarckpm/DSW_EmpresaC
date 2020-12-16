  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { EncuestadoRoutingModule } from './encuestado-routing.module';
  import { MaterialModule } from "../material.module";

  /* Componentes */
  import { EncuestadoComponent } from './componentes/raiz/encuestado.component';
  import { EncuestasPendientesComponent } from './componentes/encuestas-pendientes-page/encuestas-pendientes/encuestas-pendientes.component';

  /* Servicios */
  import { EstudiosService } from "./servicios/estudios.service";


  @NgModule({
    declarations: [
      EncuestadoComponent,
      EncuestasPendientesComponent
    ],
    imports: [
      CommonModule,
      EncuestadoRoutingModule,
      MaterialModule
    ],
    providers:[
      EstudiosService
    ]
  })
  export class EncuestadoModule { }
