import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EncuestadoRoutingModule } from './encuestado-routing.module';
import { EncuestadoComponent } from './componentes/raiz/encuestado.component';


@NgModule({
  declarations: [
    EncuestadoComponent,
    //NavbarComponent
  ],
  imports: [
    CommonModule,
    EncuestadoRoutingModule
  ]
})
export class EncuestadoModule { }
