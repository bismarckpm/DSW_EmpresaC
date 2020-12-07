import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { FooterComponent } from './componentes/footer/footer.component';
import { LoginComponent } from './componentes/login/login.component';
import { MaterialModule } from '../material.module';
import { AsideComponent } from './componentes/aside/aside.component';

import { RouterModule } from '@angular/router';
import { RecuperacionComponent } from './componentes/recuperacion/recuperacion.component';
import { RegistroComponent } from './componentes/registro/registro.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [NavbarComponent, FooterComponent, LoginComponent, AsideComponent, RecuperacionComponent, RegistroComponent],
  imports: [
 
    MaterialModule,
    RouterModule,
    CommonModule,
    ReactiveFormsModule
  ],
  exports:[NavbarComponent, FooterComponent, LoginComponent,  AsideComponent,CommonModule]
})
export class ComunModule { }
