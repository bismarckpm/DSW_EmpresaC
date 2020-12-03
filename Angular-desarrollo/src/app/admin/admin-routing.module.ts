import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { AdministrarEstudiosComponent } from "./componentes/administrar-estudios/administrar-estudios.component";

const routes: Routes = [
  {path:'', component: DashboardComponent},
  {path:'administrar-estudios', component: DashboardComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
