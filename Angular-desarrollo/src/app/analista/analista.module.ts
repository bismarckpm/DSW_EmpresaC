  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { AnalistaRoutingModule } from './analista-routing.module';
  import { MaterialModule } from '../material.module';

  /* Componentes */
  import { AnalistaComponent } from './componentes/raiz/analista.component';
import { EstudiosAsignadosComponent } from './componentes/estudios-asignados/estudios-asignados.component';
import { ConsultaEstudiosService } from './servicios/consulta-estudios/consulta-estudios.service';
import { MuestraComponent } from './componentes/estudios-asignados/muestra/muestra.component';
import { CaracteristicasComponent } from './componentes/estudios-asignados/caracteristicas/caracteristicas.component';
import { ResultadosComponent } from './componentes/estudios-asignados/resultados/resultados.component';
import { ResponderComponent } from './componentes/estudios-asignados/responder/responder.component';


  @NgModule({
    declarations: [
      AnalistaComponent,
      EstudiosAsignadosComponent,
      MuestraComponent,
      CaracteristicasComponent,
      ResultadosComponent,
      ResponderComponent
    ],
    imports: [
      CommonModule,
      AnalistaRoutingModule,
	  MaterialModule
    ],
    providers:[ConsultaEstudiosService]
  })
  export class AnalistaModule { }
