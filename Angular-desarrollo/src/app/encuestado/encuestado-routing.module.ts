import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//componentes
import { EncuestadoComponent } from './componentes/raiz/encuestado.component';
import { EncuestasPendientesComponent } from "./componentes/encuestas-pendientes-page/encuestas-pendientes/encuestas-pendientes.component";

const routes: Routes = [
  {path: '', component: EncuestadoComponent},
  {path: 'pendientes', component: EncuestasPendientesComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EncuestadoRoutingModule { }
