import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './componentes/raiz/admin.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { ComunModule } from '../comun/comun.module';
import { AdministrarEstudiosComponent } from './componentes/administrar-estudios/administrar-estudios.component';
import { MaterialModule } from '../material.module';


@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    AdministrarEstudiosComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ComunModule,
    MaterialModule
  ]
})
export class AdminModule { }
