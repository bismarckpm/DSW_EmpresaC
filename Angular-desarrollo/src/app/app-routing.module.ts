import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnalistaComponent } from './analista/analista.component';
import {EncuestadoComponent} from './encuestado/encuestado.component';
import {ClienteComponent} from './cliente/cliente.component'

const routes: Routes = [
  {path:'', component:AnalistaComponent},
  {path:'cliente-home', component:ClienteComponent},
  {path:'encuestado-home', component:EncuestadoComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
