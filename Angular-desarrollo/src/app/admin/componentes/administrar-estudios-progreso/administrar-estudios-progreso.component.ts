import { Component, OnInit } from '@angular/core';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';

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
  public admin_id:any

  constructor(private solicitudServicio:SolicitudEstudioService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.init();
    this.solicitudServicio.getEstudiosAdministrar(this.admin_id).subscribe(x=>{
      
      
      this.estudios=x.estudios
      this._toastrService.success("Exito", "Todas los estudios asignados");
      this.eventBus.cast('fin-progress','chao');
    
    },
    err=>{
      console.log(err)
      console.log("hola")
      this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      this.eventBus.cast('fin-progress','chao');
    });


  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.admin_id=localStorage.getItem('user_id');
   
  }

}
