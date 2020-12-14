import { Component, Input, OnInit } from '@angular/core';
import { Participante } from 'src/app/Entidades/participante';

@Component({
  selector: 'app-tarjeta-participantes',
  templateUrl: './tarjeta-participantes.component.html',
  styleUrls: ['./tarjeta-participantes.component.css']
})
export class TarjetaParticipantesComponent implements OnInit {
  @Input() participante:Participante;
  constructor() { }

  ngOnInit(): void {
  }

}
