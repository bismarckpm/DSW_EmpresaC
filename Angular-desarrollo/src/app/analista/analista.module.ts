import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AnalistaRoutingModule } from './analista-routing.module';
import { AnalistaComponent } from './componentes/raiz/analista.component';


@NgModule({
  declarations: [
    AnalistaComponent,
    //NavbarComponent
  ],
  imports: [
    CommonModule,
    AnalistaRoutingModule
  ]
})
export class AnalistaModule { }
