  /* Modulos */
  import { NgModule } from '@angular/core';
  import { CommonModule } from '@angular/common';
  import { AnalistaRoutingModule } from './analista-routing.module';

  /* Componentes */
  import { AnalistaComponent } from './componentes/raiz/analista.component';


  @NgModule({
    declarations: [
      AnalistaComponent
    ],
    imports: [
      CommonModule,
      AnalistaRoutingModule
    ]
  })
  export class AnalistaModule { }
