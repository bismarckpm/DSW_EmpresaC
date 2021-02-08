import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarCategoriasService } from 'src/app/admin/Servicios/administrar-categorias/administrar-categorias.service';
import { CategoriaDto } from 'src/app/Entidades/categoriaDto';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-modificar-categoria',
  templateUrl: './modificar-categoria.component.html',
  styleUrls: ['./modificar-categoria.component.css']
})
export class ModificarCategoriaComponent implements OnInit {

  public categoria:any;
  public categoria_id:any;
  public categoriaDto:CategoriaDto;

  constructor(
              public dialogRef: MatDialogRef<ModificarCategoriaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminCategoriaService:AdministrarCategoriasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.categoriaDto=new CategoriaDto();
    this.categoria=this.data.categoria.nombre;
    this.categoria_id=this.data.categoria.id;
  }

  updateCategoria(){
    
    this.categoriaDto.nombre=this.categoria;
    console.log(this.categoriaDto);
  
    this.eventBus.cast('inicio-progress','hola');
    this._adminCategoriaService.updateCategoria(this.categoria_id,this.categoriaDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Categoria actualizada");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-categoria','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta categoria ya se encuentra en el sistema", "Error");
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
