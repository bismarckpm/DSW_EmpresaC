import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdministrarCategoriasService } from 'src/app/admin/Servicios/administrar-categorias/administrar-categorias.service';
import { CategoriaDto } from 'src/app/Entidades/categoriaDto';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-anadir-modal',
  templateUrl: './anadir-modal.component.html',
  styleUrls: ['./anadir-modal.component.css']
})
export class AnadirModalComponent implements OnInit {

  public categoria:any;
  public categoriaDto: CategoriaDto; //Organizar mejor con las entidades

  constructor(private _adminCategoriaService:AdministrarCategoriasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.categoriaDto=new CategoriaDto();
  }

  addCategoria(){
    this.eventBus.cast('inicio-progress','hola');
    this.categoriaDto.nombre=this.categoria;
    console.log(this.categoriaDto);
  
    this._adminCategoriaService.addCategoria(this.categoriaDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Categoria añadida");
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
