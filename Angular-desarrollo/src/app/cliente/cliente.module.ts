import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClienteRoutingModule } from './cliente-routing.module';
import { ClienteComponent } from './componentes/raiz/cliente.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { ComunModule } from '../comun/comun.module';
import { SolicitarEstudiosComponent } from './componentes/solicitar-estudios/solicitar-estudios.component';
import { MaterialModule } from '../material.module';
import { SolicitudEstudiosService} from './servicios/solicitud_estudios/solicitud-estudios.service';
import { NgProgressModule } from 'ngx-progressbar';
import { NgProgressHttpModule } from 'ngx-progressbar/http';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    ClienteComponent,
    DashboardComponent,
    SolicitarEstudiosComponent
  ],
  imports: [
    CommonModule,
    ClienteRoutingModule,
    ComunModule,
    MaterialModule,
    NgProgressModule,
    NgProgressHttpModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-center',
      progressBar:true,
      closeButton:true
    }),
    FormsModule
    
  ],
  providers:[SolicitudEstudiosService],
  bootstrap: [ClienteComponent]
})
export class ClienteModule { }
