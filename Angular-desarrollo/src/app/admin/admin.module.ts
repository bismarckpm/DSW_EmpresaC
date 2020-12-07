import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './componentes/raiz/admin.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { ComunModule } from '../comun/comun.module';
import { AdministrarEstudiosComponent } from './componentes/administrar-estudios/administrar-estudios.component';
<<<<<<< HEAD
import { MaterialModule } from '../material.module';
=======

import { MaterialModule } from '.././material.module';
import { TarjetaEstudiosComponent } from './componentes/tarjeta-estudios/tarjeta-estudios.component';
>>>>>>> e552ff9549ceb29e06aaa190c8f897664ea700b6


import { AsignarEncuestaComponent } from './componentes/asignar-encuesta/asignar-encuesta.component';
import { AdministrarEstudiosProgresoComponent } from './componentes/administrar-estudios-progreso/administrar-estudios-progreso.component';
import { TarjetaEstudioProgresoComponent } from './componentes/tarjeta-estudio-progreso/tarjeta-estudio-progreso.component';
import { EstudioDetalleComponent } from './componentes/estudio-detalle/estudio-detalle.component';


//Servicios
import { SolicitudEstudioService } from "./Servicios/solicitud-estudio.service";
import { PreguntaService } from "./Servicios/pregunta.service";
import { ContenedorPreguntaNuevaComponent } from './componentes/Preguntas/contenedor-pregunta-nueva/contenedor-pregunta-nueva.component';
import { ContenedorPreguntaViejaComponent } from './componentes/Preguntas/contenedor-pregunta-vieja/contenedor-pregunta-vieja.component';

@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    AdministrarEstudiosComponent,
    TarjetaEstudiosComponent,
    AsignarEncuestaComponent,
    AdministrarEstudiosProgresoComponent,
    TarjetaEstudioProgresoComponent,
    EstudioDetalleComponent,
    ContenedorPreguntaNuevaComponent,
    ContenedorPreguntaViejaComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ComunModule,
<<<<<<< HEAD
    MaterialModule
=======
    MaterialModule,
    ReactiveFormsModule
  ],
  providers:[SolicitudEstudioService,
  PreguntaService
>>>>>>> e552ff9549ceb29e06aaa190c8f897664ea700b6
  ]
})
export class AdminModule { }
