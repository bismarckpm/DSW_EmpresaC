import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EncuestadoComponent } from './componentes/raiz/encuestado.component';

const routes: Routes = [
  {path: '', component: EncuestadoComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EncuestadoRoutingModule { }
