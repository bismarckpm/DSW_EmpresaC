import { Component, OnInit } from '@angular/core';

import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";
import { LoginService } from "../../../comun/servicios/login/login.service";

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
//Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Estudio } from "../../../Entidades/estudio";



@Component({
  selector: 'admin-administrar-estudios',
  templateUrl: './administrar-estudios.component.html',
  styleUrls: ['./administrar-estudios.component.css']

})
export class AdministrarEstudiosComponent implements OnInit {
  estudios:Estudio[];
  public admin_id:any

  constructor(private solicitudservice:SolicitudEstudioService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) { }

  ngOnInit(): void {
    this.init();

    this.solicitudservice.getEstudiosPendientes(this.admin_id).subscribe(e=> {
      this._toastrService.success("Exito", "Todas los estudios asignados");
      this.eventBus.cast('fin-progress','chao');
      this.estudios=e.estudios;console.log(e)},
      error=>{

        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});

        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          this.eventBus.cast('fin-progress','chao');
        }

      })
  }


  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.admin_id=localStorage.getItem('user_id');
   
  }
}
