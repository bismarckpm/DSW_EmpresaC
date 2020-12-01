import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './componentes/raiz/admin.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { AsideComponent } from './componentes/aside/aside.component';
import { ContenidoComponent } from './componentes/contenido/contenido.component';
import { ComunModule } from '../comun/comun.module';



@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    AsideComponent,
    ContenidoComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ComunModule
  ]
})
export class AdminModule { }
