import { Component, Input, OnInit } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';

//Entidades
import { Pregunta } from "../../../../Entidades/pregunta";
@Component({
  selector: 'app-contenedor-pregunta-vieja',
  templateUrl: './contenedor-pregunta-vieja.component.html',
  styleUrls: ['./contenedor-pregunta-vieja.component.css']
})
export class ContenedorPreguntaViejaComponent implements OnInit {
  @Input() pregunta:Pregunta;
  @Output() preguntaEvent = new EventEmitter<Pregunta>();

  constructor() { }

  ngOnInit(): void {
    console.log(this.pregunta);
  }

  Eliminar(){
    this.preguntaEvent.emit(this.pregunta);
  }

}
