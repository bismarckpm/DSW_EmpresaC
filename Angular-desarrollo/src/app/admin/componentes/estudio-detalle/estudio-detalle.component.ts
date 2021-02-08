import { Component, OnInit } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';


// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Estudio } from "../../../Entidades/estudio";

//Servicio 
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";
import { Participante } from 'src/app/Entidades/participante';
import { LoginService } from "../../../comun/servicios/login/login.service";


@Component({
  selector: 'app-estudio-detalle',
  templateUrl: './estudio-detalle.component.html',
  styleUrls: ['./estudio-detalle.component.css']
})
export class EstudioDetalleComponent implements OnInit {
  estudio:Estudio;
  participantes:Participante[]=[];
  error:string;
  activos:number;
  inactivos:number;
  public admin_id:any

  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) {
      this.init();
      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
      .subscribe(x => { 
        
        this.estudio = x.estudio;
        this._toastrService.success("Exito", "Todas los estudios asignados");
        this.eventBus.cast('fin-progress','chao');
      
      },error=>{

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

      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getParticipantes(params['id']); }))
      .subscribe(x => { this.participantes = x.Participantes; console.log(this.participantes)
      this.activos=this.participantes.filter(x=>x.Estado=="activo").length
      this.inactivos=this.participantes.filter(x=>x.Estado=="inactivo").length
      console.log(x);
      
      },error=>{
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

  ngOnInit(): void {
  }

  eliminarEstudio(id:number){
    this.eventBus.cast('inicio-progress','hola');
    this.solicitudServicio.DeleteEstudios(this.estudio.id).subscribe(x=>{
      console.log(x.codigo)
      this._toastrService.success("Exito", "Estudio Cancelado");
      this.eventBus.cast('fin-progress','chao');
      this.location.back();
    },error=>{
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
