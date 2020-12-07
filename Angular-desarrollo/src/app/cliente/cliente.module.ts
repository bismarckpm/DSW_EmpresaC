import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClienteRoutingModule } from './cliente-routing.module';
import { ClienteComponent } from './componentes/raiz/cliente.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { ComunModule } from '../comun/comun.module';
import { SolicitarEstudiosComponent } from './componentes/solicitar-estudios/solicitar-estudios.component';
import { MaterialModule } from '../material.module';




@NgModule({
  declarations: [
    ClienteComponent,
    DashboardComponent,
    SolicitarEstudiosComponent
    //NavbarComponent
  ],
  imports: [
    CommonModule,
    ClienteRoutingModule,
    ComunModule,
    MaterialModule
    
  ]
})
export class ClienteModule { }
