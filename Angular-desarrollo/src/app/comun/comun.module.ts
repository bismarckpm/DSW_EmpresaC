  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { MaterialModule } from '../material.module';
  import { RouterModule } from '@angular/router';
  import { ReactiveFormsModule } from '@angular/forms';
  import { FormsModule } from '@angular/forms';

  /* Componentes */
  import { AsideComponent } from './componentes/aside/aside.component';
  import { NavbarComponent } from './componentes/navbar/navbar.component';
  import { FooterComponent } from './componentes/footer/footer.component';
  import { LoginComponent } from './componentes/login/login.component';
  import { RecuperacionComponent } from './componentes/recuperacion/recuperacion.component';
  import { RegistroComponent } from './componentes/registro/registro.component';
import { ModalPasswordComponent } from './componentes/aside/modal-password/modal-password.component';



  @NgModule({
    declarations: [NavbarComponent, FooterComponent, LoginComponent, AsideComponent, RecuperacionComponent, RegistroComponent, ModalPasswordComponent],
    imports: [
  
      MaterialModule,
      RouterModule,
      CommonModule,
      ReactiveFormsModule,
      FormsModule
    ],
    exports:[NavbarComponent, FooterComponent, LoginComponent,  AsideComponent,CommonModule]
  })
  export class ComunModule { }
