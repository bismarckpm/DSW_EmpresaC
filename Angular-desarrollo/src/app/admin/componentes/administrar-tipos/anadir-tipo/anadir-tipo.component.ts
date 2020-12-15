import { Component, OnInit } from '@angular/core';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarTiposService } from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';
import { TipoDto } from 'src/app/Entidades/TipoDto';

@Component({
  selector: 'app-anadir-tipo',
  templateUrl: './anadir-tipo.component.html',
  styleUrls: ['./anadir-tipo.component.css']
})
export class AnadirTipoComponent implements OnInit {

  public tipo:any;
  public tipoDto: TipoDto; //Organizar mejor con las entidades

  constructor(private _adminTipoService:AdministrarTiposService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) {}

  ngOnInit(): void {
    this.tipoDto=new TipoDto();
  }

  addTipo(){
    this.eventBus.cast('inicio-progress','hola');
    this.tipoDto.nombre=this.tipo;
    console.log(this.tipoDto);
  
    this._adminTipoService.addTipo(this.tipoDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Tipo añadido");
				this._toastrService.info('Espero un momento, por favor.','Actualizando...');
				this.eventBus.cast('actualizar-tipo','actualizar');
		  }
		  else{
			  this._toastrService.error("Este tipo ya se encuentra en el sistema", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-categoria-dialog','cerrar');
      });
  }
}
