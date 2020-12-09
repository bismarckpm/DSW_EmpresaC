import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from '../cliente/componentes/dashboard/dashboard.component';
import { ClienteComponent } from './componentes/raiz/cliente.component';
import { SolicitarEstudiosComponent } from "./componentes/solicitar-estudios/solicitar-estudios.component";

const routes: Routes = [
  {path:'', component: DashboardComponent},
  {path:'solicitar-estudios', component: SolicitarEstudiosComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClienteRoutingModule { }
