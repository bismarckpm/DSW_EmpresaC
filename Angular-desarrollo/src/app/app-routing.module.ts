import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './comun/componentes/login/login.component';
import {RecuperacionComponent } from './comun/componentes/recuperacion/recuperacion.component';
import {RegistroComponent } from './comun/componentes/registro/registro.component';
import { Page404Component } from "./comun/componentes/page404/page404.component";
import { Page401Component } from "./comun/componentes/page401/page401.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  {path:'login', component: LoginComponent},
  {path:'recuperacion', component: RecuperacionComponent},
  {path:'registro', component: RegistroComponent},
  {
    path:'admin',
    loadChildren: () => import
    ('./admin/admin.module').then(m => m.AdminModule)
  },
  {
    path:'analista',
    loadChildren: () => import
    ('./analista/analista.module').then(m => m.AnalistaModule)
  },
  {
    path:'cliente',
    loadChildren: () => import
    ('./cliente/cliente.module').then(m => m.ClienteModule)
  }, 
  
  {
    path:'encuestado',
    loadChildren: () => import
    ('./encuestado/encuestado.module').then(m => m.EncuestadoModule)
  },
  {path: '404', component: Page404Component},
  {path: '401', component: Page401Component},
  {path: '**', redirectTo: '/404'}
];
//import { NavbarComponent } from './navbar/navbar.component';
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
