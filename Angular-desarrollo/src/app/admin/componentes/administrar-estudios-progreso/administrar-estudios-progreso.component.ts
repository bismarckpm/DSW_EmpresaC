import { Component, OnInit } from '@angular/core';

//Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Respuesta } from "../../../Entidades/respuesta";
import { Estudio } from "../../../Entidades/estudio";
//Servicios
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";

@Component({
  selector: 'app-administrar-estudios-progreso',
  templateUrl: './administrar-estudios-progreso.component.html',
  styleUrls: ['./administrar-estudios-progreso.component.css']
})
export class AdministrarEstudiosProgresoComponent implements OnInit {
  estudios:Estudio[];
  constructor(private solicitudServicio:SolicitudEstudioService) { }

  ngOnInit(): void {
    this.solicitudServicio.getEstudiosAdministrar().subscribe(x=> this.estudios=x.estudios);


  }

}
