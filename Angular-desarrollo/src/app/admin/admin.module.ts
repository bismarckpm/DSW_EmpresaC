import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './componentes/raiz/admin.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { AsideComponent } from './componentes/aside/aside.component';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { ContenidoComponent } from './componentes/contenido/contenido.component';
import { FooterComponent } from './componentes/footer/footer.component';




@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    AsideComponent,
    NavbarComponent,
    ContenidoComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
