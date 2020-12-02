import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './componentes/raiz/admin.component';
import { DashboardComponent } from './componentes/dashboard/dashboard.component';
import { ComunModule } from '../comun/comun.module';
import { AdministrarEstudiosComponent } from './componentes/administrar-estudios/administrar-estudios.component';

import { MaterialModule } from '.././material.module';
import { TarjetaEstudiosComponent } from './componentes/tarjeta-estudios/tarjeta-estudios.component';

import { SolicitudEstudioService } from "./Servicios/solicitud-estudio.service";
import { AsignarEncuestaComponent } from './componentes/asignar-encuesta/asignar-encuesta.component';
import { AdministrarEstudiosProgresoComponent } from './componentes/administrar-estudios-progreso/administrar-estudios-progreso.component';
import { TarjetaEstudioProgresoComponent } from './componentes/tarjeta-estudio-progreso/tarjeta-estudio-progreso.component';
import { EstudioDetalleComponent } from './componentes/estudio-detalle/estudio-detalle.component';

@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    AdministrarEstudiosComponent,
    TarjetaEstudiosComponent,
    AsignarEncuestaComponent,
    AdministrarEstudiosProgresoComponent,
    TarjetaEstudioProgresoComponent,
    EstudioDetalleComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ComunModule,
    MaterialModule
  ],
  providers:[SolicitudEstudioService]
})
export class AdminModule { }
