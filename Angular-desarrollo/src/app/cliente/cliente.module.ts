  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { ClienteRoutingModule } from './cliente-routing.module';
  import { FormsModule } from '@angular/forms';
  import { MaterialModule } from '../material.module';

  /* Componentes */
  import { ClienteComponent } from './componentes/raiz/cliente.component';
  import { DashboardComponent } from './componentes/dashboard/dashboard.component';
  import { SolicitarEstudiosComponent } from './componentes/solicitar-estudios/solicitar-estudios.component';


  /* Servicios */
  import { SolicitudEstudiosService} from './servicios/solicitud_estudios/solicitud-estudios.service';
import { ConsultarEstudiosComponent } from './componentes/consultar-estudios/consultar-estudios.component';

  @NgModule({
    declarations: [
      ClienteComponent,
      DashboardComponent,
      SolicitarEstudiosComponent,
      ConsultarEstudiosComponent
    ],
    imports: [
      CommonModule,
      ClienteRoutingModule,
      FormsModule,
      MaterialModule
      
    ],
    providers:[SolicitudEstudiosService],
    bootstrap: [ClienteComponent]
  })
  export class ClienteModule { }
