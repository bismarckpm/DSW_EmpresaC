import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-eliminar-marca',
  templateUrl: './eliminar-marca.component.html',
  styleUrls: ['./eliminar-marca.component.css']
})
export class EliminarMarcaComponent implements OnInit {


  public marca_id:any;
  constructor(
              public dialogRef: MatDialogRef<EliminarMarcaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminMarcasService: AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
    ) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marca_id=this.data.marca.id;
  }

  deleteMarca(){
    this.eventBus.cast('inicio-progress','hola');
    this._adminMarcasService.deleteMarca(this.marca_id).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Marca inactiva");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-marca','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta marca no se puede eliminar/inhabilitar", "Error");
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
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          this.eventBus.cast('fin-progress','chao');
        }
      });

  }
  



}
