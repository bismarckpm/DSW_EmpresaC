import { Component, Input, OnInit } from '@angular/core';
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";

@Component({
  selector: 'app-tarjeta-estudio-progreso',
  templateUrl: './tarjeta-estudio-progreso.component.html',
  styleUrls: ['./tarjeta-estudio-progreso.component.css']
})
export class TarjetaEstudioProgresoComponent implements OnInit {
  @Input() estudio:SolicitudEstudio;
  
  constructor() { }

  ngOnInit(): void {
  }

}
