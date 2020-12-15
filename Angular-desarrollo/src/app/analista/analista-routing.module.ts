import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnalistaComponent } from './componentes/raiz/analista.component';

//componentes
import { EstudiosAsignadosComponent } from './componentes/estudios-asignados/estudios-asignados.component'
import { EstudiosTelefonicosComponent } from "./componentes/estudios-telefonicos/estudios-telefonicos.component";
import { ParticipantesEstudioComponent } from "./componentes/participantes-estudio/participantes-estudio.component";
const routes: Routes = [
  {path:'', component: AnalistaComponent} ,
  {path:'estudios',component:EstudiosAsignadosComponent},
  {path:'estudios-telefonicos',component:EstudiosTelefonicosComponent},
  { path: 'Encuestar/:id',     component:ParticipantesEstudioComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnalistaRoutingModule { }
