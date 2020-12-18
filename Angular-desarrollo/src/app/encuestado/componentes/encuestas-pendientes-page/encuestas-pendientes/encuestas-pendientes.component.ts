import { Component, OnInit } from '@angular/core';

//entidades
import { Estudio } from "../../../../Entidades/estudio";

// servicios 
import { EstudiosService } from "../../../servicios/estudios.service";

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-encuestas-pendientes',
  templateUrl: './encuestas-pendientes.component.html',
  styleUrls: ['./encuestas-pendientes.component.css']
})
export class EncuestasPendientesComponent implements OnInit {
  estudios:Estudio[];
  public encuestado_id:any

  constructor(private servicioEstudio:EstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.init();


    this.servicioEstudio.getEstudiosPendiente(this.encuestado_id).subscribe(x=>{
      this.estudios=x.estudios;
      console.log(x)
      this._toastrService.success("Exito", "Todas los estudios asignados");
      this.eventBus.cast('fin-progress','chao');
    },
    err=>{
      this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      this.eventBus.cast('fin-progress','chao');

    })


  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.encuestado_id=localStorage.getItem('user_id');
   
  }

}
