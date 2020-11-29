import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './comun/componentes/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  {path:'login', component: LoginComponent},
  {
    path:'admin-home',
    loadChildren: () => import
    ('./admin/admin.module').then(m => m.AdminModule)
  },
  {
    path:'analista-home',
    loadChildren: () => import
    ('./analista/analista.module').then(m => m.AnalistaModule)
  },
  {
    path:'cliente-home',
    loadChildren: () => import
    ('./cliente/cliente.module').then(m => m.ClienteModule)
  },
  {
    path:'encuestado-home',
    loadChildren: () => import
    ('./encuestado/encuestado.module').then(m => m.EncuestadoModule)
  }
];
//import { NavbarComponent } from './navbar/navbar.component';
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
