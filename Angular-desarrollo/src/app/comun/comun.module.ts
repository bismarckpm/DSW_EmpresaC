import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { FooterComponent } from './componentes/footer/footer.component';
import { LoginComponent } from './componentes/login/login.component';
import { MaterialModule } from '../material.module';



@NgModule({
  declarations: [NavbarComponent, FooterComponent, LoginComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports:[NavbarComponent, FooterComponent, LoginComponent]
})
export class ComunModule { }
