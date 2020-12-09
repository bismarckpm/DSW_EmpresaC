import { Component, Input, OnInit } from '@angular/core';


//Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";

@Component({
  selector: 'app-tarjeta-estudios',
  templateUrl: './tarjeta-estudios.component.html',
  styleUrls: ['./tarjeta-estudios.component.css']
})
export class TarjetaEstudiosComponent implements OnInit {
  @Input() estudio:any;
  constructor() { }

  ngOnInit(): void {
  }

}
