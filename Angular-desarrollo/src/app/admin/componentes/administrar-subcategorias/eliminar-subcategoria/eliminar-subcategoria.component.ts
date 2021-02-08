import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-eliminar-subcategoria',
  templateUrl: './eliminar-subcategoria.component.html',
  styleUrls: ['./eliminar-subcategoria.component.css']
})
export class EliminarSubcategoriaComponent implements OnInit {

  public subcategoria_id:any;
  constructor(
            public dialogRef: MatDialogRef<EliminarSubcategoriaComponent>,
            @Inject(MAT_DIALOG_DATA) public data: any,
            private _adminSubcategoriasService:AdministrarSubcategoriasService,
            private _toastrService: ToastrService,
            private eventBus: NgEventBus,
            private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.subcategoria_id=this.data.subcategoria.id;
  }

  deleteSubcategoria(){
    this.eventBus.cast('inicio-progress','hola');
    this._adminSubcategoriasService.deleteSubcategoria(this.subcategoria_id).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Subcategoria inactiva");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-subcategoria','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta subcategoria no se puede eliminar/inhabilitar", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        this.eventBus.cast('cerrar-subcategoria-add','cerrar');

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
