import { Component, OnInit } from '@angular/core';

import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";

//Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";

@Component({
  selector: 'admin-administrar-estudios',
  templateUrl: './administrar-estudios.component.html',
  styleUrls: ['./administrar-estudios.component.css']
})
export class AdministrarEstudiosComponent implements OnInit {
  estudios:SolicitudEstudio[];
  constructor(private solicitudservice:SolicitudEstudioService) { }

  ngOnInit(): void {
    this.solicitudservice.getEstudiosPendientes().subscribe(e=> {this.estudios=e ;console.log(this.estudios ) });
    
  }

}
