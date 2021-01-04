  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { ClienteRoutingModule } from './cliente-routing.module';
  import { FormsModule } from '@angular/forms';
  import { MaterialModule } from '../material.module';
  import { GoogleChartsModule } from 'angular-google-charts';

  /* Componentes */
  import { ClienteComponent } from './componentes/raiz/cliente.component';
  import { DashboardComponent } from './componentes/dashboard/dashboard.component';
  import { SolicitarEstudiosComponent } from './componentes/solicitar-estudios/solicitar-estudios.component';
  import { GraficosComponent } from './componentes/consultar-estudios/graficos/graficos.component';
  import { IndividualComponent } from './componentes/consultar-estudios/individual/individual.component';
  import { MuestraComponent } from './componentes/consultar-estudios/muestra/muestra.component';
  import { CaracteristicasComponent } from './componentes/consultar-estudios/caracteristicas/caracteristicas.component';
  import {RespuestasPersonaComponent} from './componentes/consultar-estudios/individual/respuestas-persona/respuestas-persona.component';
  
  /* Servicios */
  import { SolicitudEstudiosService} from './servicios/solicitud_estudios/solicitud-estudios.service';
  import { ConsultarEstudiosComponent } from './componentes/consultar-estudios/consultar-estudios.component';
import { ResultadosAnalistaComponent } from './componentes/consultar-estudios/resultados-analista/resultados-analista.component';

  @NgModule({
    declarations: [
      ClienteComponent,
      DashboardComponent,
      SolicitarEstudiosComponent,
      ConsultarEstudiosComponent,
      GraficosComponent,
      IndividualComponent,
      MuestraComponent,
      CaracteristicasComponent,
      RespuestasPersonaComponent,
      ResultadosAnalistaComponent
    ],
    imports: [
      CommonModule,
      ClienteRoutingModule,
      FormsModule,
      MaterialModule,
      GoogleChartsModule
    ],
    providers:[SolicitudEstudiosService],
    bootstrap: [ClienteComponent]
  })
  export class ClienteModule { }
