import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './componentes/navbar/navbar.component';
import { FooterComponent } from './componentes/footer/footer.component';
import { LoginComponent } from './componentes/login/login.component';
import { MaterialModule } from '../material.module';
import { AsideComponent } from './componentes/aside/aside.component';

import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [NavbarComponent, FooterComponent, LoginComponent, AsideComponent],
  imports: [
    
    MaterialModule,
    RouterModule,
    
  ],
  exports:[NavbarComponent, FooterComponent, LoginComponent,  AsideComponent]
})
export class ComunModule { }
