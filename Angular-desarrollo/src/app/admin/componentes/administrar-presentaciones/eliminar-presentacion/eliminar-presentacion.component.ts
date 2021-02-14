import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarPresentacionService } from 'src/app/admin/Servicios/administrar-presentacion/administrar-presentacion.service';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-eliminar-presentacion',
  templateUrl: './eliminar-presentacion.component.html',
  styleUrls: ['./eliminar-presentacion.component.css']
})
export class EliminarPresentacionComponent implements OnInit {

  public presentacion_id:any;
  constructor(
              public dialogRef: MatDialogRef<EliminarPresentacionComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminPresentacionService:AdministrarPresentacionService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.presentacion_id=this.data.presentacion.id;
  }

  deletePresentacion(){
    this.eventBus.cast('inicio-progress','hola');
    this._adminPresentacionService.deletePresentacion(this.presentacion_id).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Presentacion inactiva");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-presentacion','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta presentacion no se puede eliminar/inhabilitar", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          if(error.error.mensaje){
            this._toastrService.error("Ops! Hubo un problema.", error.error.mensaje)
          }
          else{
            this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          }
          this.eventBus.cast('fin-progress','chao');
        }
      });

  }

}
