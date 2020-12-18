import { Component, OnInit } from '@angular/core';


//Entidades
import { Estudio } from "../../../Entidades/estudio";

//Servicios
import { ConsultaEstudiosService } from "../../servicios/consulta-estudios/consulta-estudios.service";


@Component({
  selector: 'app-estudios-telefonicos',
  templateUrl: './estudios-telefonicos.component.html',
  styleUrls: ['./estudios-telefonicos.component.css']
})
export class EstudiosTelefonicosComponent implements OnInit {
  estudios:Estudio[]

  constructor(private estudioService:ConsultaEstudiosService) { }

  ngOnInit(): void {
    this.estudioService.getEstudiosTelefonicos(11).subscribe(x=>{
      this.estudios=x.estudios;
      console.log(this.estudios)


    })


  }

}
