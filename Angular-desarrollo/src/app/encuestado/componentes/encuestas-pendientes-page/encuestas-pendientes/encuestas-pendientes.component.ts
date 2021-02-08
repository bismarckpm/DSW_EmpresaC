import { Component, OnInit } from '@angular/core';

//entidades
import { Estudio } from "../../../../Entidades/estudio";

// servicios 
import { EstudiosService } from "../../../servicios/estudios.service";
import { LoginService } from "../../../../comun/servicios/login/login.service";

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-encuestas-pendientes',
  templateUrl: './encuestas-pendientes.component.html',
  styleUrls: ['./encuestas-pendientes.component.css']
})
export class EncuestasPendientesComponent implements OnInit {
  estudios:Estudio[];
  public encuestado_id:any;
  public user_id:any;

  constructor(private servicioEstudio:EstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.getEncuestadoId();
  }

  getEstudiosPendientes(){
    this.servicioEstudio.getEstudiosPendiente(this.encuestado_id).subscribe(x=>{
      this.estudios=x.estudios;
      console.log(x)
      this._toastrService.success("Exito", "Todas los estudios pendientes");
      this.eventBus.cast('fin-progress','chao');
    },
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

  getEncuestadoId(){
    this.user_id=+localStorage.getItem('user_id');
    this.servicioEstudio.getEncuestadoId(this.user_id).subscribe(
      (response)=>{
        console.log(response);
        this.encuestado_id=response.encuestado_id;
        console.log('Encuestado: '+this.encuestado_id);
        this.getEstudiosPendientes();
      },
      (error)=>{
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
      });
  }

}
