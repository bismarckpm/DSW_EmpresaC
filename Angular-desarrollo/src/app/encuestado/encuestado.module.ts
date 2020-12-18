  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { EncuestadoRoutingModule } from './encuestado-routing.module';
  import { MaterialModule } from "../material.module";
  import { ReactiveFormsModule } from '@angular/forms';
  import { FormsModule } from '@angular/forms';

  /* Componentes */
  import { EncuestadoComponent } from './componentes/raiz/encuestado.component';
  import { EncuestasPendientesComponent } from './componentes/encuestas-pendientes-page/encuestas-pendientes/encuestas-pendientes.component';

  /* Servicios */
  import { EstudiosService } from "./servicios/estudios.service";
import { EncuestaPageComponent } from './componentes/encuesta-page/encuesta-page.component';


  @NgModule({
    declarations: [
      EncuestadoComponent,
      EncuestasPendientesComponent,
      EncuestaPageComponent
    ],
    imports: [
      CommonModule,
      EncuestadoRoutingModule,
      MaterialModule,
      ReactiveFormsModule,
      FormsModule
    ],
    providers:[
      EstudiosService
    ]
  })
  export class EncuestadoModule { }
