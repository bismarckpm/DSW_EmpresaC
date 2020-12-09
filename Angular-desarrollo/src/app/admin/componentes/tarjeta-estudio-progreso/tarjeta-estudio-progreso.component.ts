import { Component, Input, OnInit } from '@angular/core';
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";

import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";

@Component({
  selector: 'app-tarjeta-estudio-progreso',
  templateUrl: './tarjeta-estudio-progreso.component.html',
  styleUrls: ['./tarjeta-estudio-progreso.component.css']
})
export class TarjetaEstudioProgresoComponent implements OnInit {
  @Input() estudio:any;
  
  
  constructor(private estudioService:SolicitudEstudioService) { }

  ngOnInit(): void {
  }

  eliminarEstudio(id:number){
    this.estudioService.DeleteEstudios(this.estudio.id).subscribe(x=>{
      console.log(x.codigo)
    })

  }

}
