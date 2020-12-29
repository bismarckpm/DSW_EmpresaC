  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { AnalistaRoutingModule } from './analista-routing.module';
  import { MaterialModule } from '../material.module';
  import { ReactiveFormsModule } from '@angular/forms';
  import { FormsModule } from '@angular/forms';

  /* Componentes */
  import { AnalistaComponent } from './componentes/raiz/analista.component';
import { EstudiosAsignadosComponent } from './componentes/estudios-asignados/estudios-asignados.component';
import { ConsultaEstudiosService } from './servicios/consulta-estudios/consulta-estudios.service';
import { MuestraComponent } from './componentes/estudios-asignados/muestra/muestra.component';
import { CaracteristicasComponent } from './componentes/estudios-asignados/caracteristicas/caracteristicas.component';
import { ResultadosComponent } from './componentes/estudios-asignados/resultados/resultados.component';
import { ResponderComponent } from './componentes/estudios-asignados/responder/responder.component';
import { EstudiosTelefonicosComponent } from './componentes/estudios-telefonicos/estudios-telefonicos.component';
import { ParticipantesEstudioComponent } from './componentes/participantes-estudio/participantes-estudio.component';
import { EncuestaTelefonicaComponent } from './componentes/encuesta-telefonica/encuesta-telefonica.component';
import { GraficosComponent } from './componentes/estudios-asignados/graficos/graficos.component';
import { IndividualComponent } from './componentes/estudios-asignados/individual/individual.component';
import { RespuestasPersonaComponent } from './componentes/estudios-asignados/individual/respuestas-persona/respuestas-persona.component';


  @NgModule({
    declarations: [
      AnalistaComponent,
      EstudiosAsignadosComponent,
      MuestraComponent,
      CaracteristicasComponent,
      ResultadosComponent,
      ResponderComponent,
      EstudiosTelefonicosComponent,
      ParticipantesEstudioComponent,
      EncuestaTelefonicaComponent,
      GraficosComponent,
      IndividualComponent,
      RespuestasPersonaComponent,

    ],
    imports: [
      CommonModule,
      AnalistaRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
    ],
    providers:[ConsultaEstudiosService]
  })
  export class AnalistaModule { }
