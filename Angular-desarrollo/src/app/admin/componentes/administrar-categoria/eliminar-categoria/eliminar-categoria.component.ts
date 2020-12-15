import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarCategoriasService } from 'src/app/admin/Servicios/administrar-categorias/administrar-categorias.service';

@Component({
  selector: 'app-eliminar-categoria',
  templateUrl: './eliminar-categoria.component.html',
  styleUrls: ['./eliminar-categoria.component.css']
})
export class EliminarCategoriaComponent implements OnInit {

  public categoria_id:any;
  constructor(
              public dialogRef: MatDialogRef<EliminarCategoriaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminCategoriaService:AdministrarCategoriasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) {}

  ngOnInit(): void {
    this.categoria_id=this.data.categoria.id;
  }

  deleteCategoria(){
    this.eventBus.cast('inicio-progress','hola');
    this._adminCategoriaService.deleteCategoria(this.categoria_id).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Categoria inactiva");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-categoria','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta categoria no se puede eliminar/inhabilitar", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-categoria-add','cerrar');
      });

  }

}
