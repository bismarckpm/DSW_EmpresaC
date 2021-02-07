import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarTiposService } from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-eliminar-tipo',
  templateUrl: './eliminar-tipo.component.html',
  styleUrls: ['./eliminar-tipo.component.css']
})
export class EliminarTipoComponent implements OnInit {

  public tipo_id:any;
  constructor(
              public dialogRef: MatDialogRef<EliminarTipoComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminTipoService:AdministrarTiposService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.tipo_id=this.data.tipo.id;
  }

  deleteTipo(){
    this.eventBus.cast('inicio-progress','hola');
    this._adminTipoService.deleteTipo(this.tipo_id).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Tipo inactivo");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-tipo','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta tipo no se puede eliminar/inhabilitar", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        this.eventBus.cast('cerrar-tipo-add','cerrar');

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
