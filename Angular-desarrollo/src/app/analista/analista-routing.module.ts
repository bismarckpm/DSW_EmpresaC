import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnalistaComponent } from './componentes/raiz/analista.component';
import { EstudiosAsignadosComponent } from './componentes/estudios-asignados/estudios-asignados.component'

const routes: Routes = [
  {path:'', component: AnalistaComponent} ,
  {path:'estudios',component:EstudiosAsignadosComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnalistaRoutingModule { }
