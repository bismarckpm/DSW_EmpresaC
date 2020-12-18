import { Component, Input, OnInit } from '@angular/core';
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";


//Entidad
import { Estudio } from "../../../Entidades/estudio";

import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";

@Component({
  selector: 'app-tarjeta-estudio-progreso',
  templateUrl: './tarjeta-estudio-progreso.component.html',
  styleUrls: ['./tarjeta-estudio-progreso.component.css']
})
export class TarjetaEstudioProgresoComponent implements OnInit {
  @Input() estudio:Estudio;
  
  
  constructor(private estudioService:SolicitudEstudioService) { }

  ngOnInit(): void {
  }

  eliminarEstudio(id:number){
    this.estudioService.DeleteEstudios(this.estudio.id).subscribe(x=>{
      console.log(x.codigo)
    })

  }

}
