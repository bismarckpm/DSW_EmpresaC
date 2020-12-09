import { Component, OnInit } from '@angular/core';

import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";

//Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { AnySrvRecord } from 'dns';

@Component({
  selector: 'admin-administrar-estudios',
  templateUrl: './administrar-estudios.component.html',
  styleUrls: ['./administrar-estudios.component.css']
})
export class AdministrarEstudiosComponent implements OnInit {
  estudios:any[];
  constructor(private solicitudservice:SolicitudEstudioService) { }

  ngOnInit(): void {

    this.solicitudservice.getEstudiosAdministrar2().subscribe(e=> {this.estudios=e.categorias;console.log(e.categorias[0].encuesta)})
  }

}
