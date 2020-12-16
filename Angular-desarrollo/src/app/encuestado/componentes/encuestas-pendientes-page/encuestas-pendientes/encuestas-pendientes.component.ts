import { Component, OnInit } from '@angular/core';

//entidades
import { Estudio } from "../../../../Entidades/estudio";

// servicios 
import { EstudiosService } from "../../../servicios/estudios.service";

@Component({
  selector: 'app-encuestas-pendientes',
  templateUrl: './encuestas-pendientes.component.html',
  styleUrls: ['./encuestas-pendientes.component.css']
})
export class EncuestasPendientesComponent implements OnInit {
  estudios:Estudio[];

  constructor(private servicioEstudio:EstudiosService) { }

  ngOnInit(): void {

    this.servicioEstudio.getEstudiosPendiente().subscribe(x=>{
      this.estudios=x.estudios;
    })


  }

}
