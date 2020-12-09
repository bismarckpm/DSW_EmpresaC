  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { EncuestadoRoutingModule } from './encuestado-routing.module';

  /* Componentes */
  import { EncuestadoComponent } from './componentes/raiz/encuestado.component';

  @NgModule({
    declarations: [
      EncuestadoComponent
    ],
    imports: [
      CommonModule,
      EncuestadoRoutingModule
    ]
  })
  export class EncuestadoModule { }
