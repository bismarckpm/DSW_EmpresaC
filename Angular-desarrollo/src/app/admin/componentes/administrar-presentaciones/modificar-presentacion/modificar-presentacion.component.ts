import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarPresentacionService } from 'src/app/admin/Servicios/administrar-presentacion/administrar-presentacion.service';
import { AdministrarTiposService } from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';
import { PresentacionDto } from 'src/app/Entidades/presentacionDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-modificar-presentacion',
  templateUrl: './modificar-presentacion.component.html',
  styleUrls: ['./modificar-presentacion.component.css']
})
export class ModificarPresentacionComponent implements OnInit {

  public presentacion:any;
  public tipo_id:any;
  public presentacion_id:any;
  public tipos:any[];
  public presentacionDto: PresentacionDto; 
  public tipoDto:TipoDto;
  public tipos_filtered: any;

    constructor(
                public dialogRef: MatDialogRef<ModificarPresentacionComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                private _adminPresentacionService:AdministrarPresentacionService,
                private _adminTipoService: AdministrarTiposService, 
                private _toastrService: ToastrService,
                private eventBus: NgEventBus,
                private loginService:LoginService
    ) {}

    ngOnInit(): void {
      this.init();
    }

    init(){
      this.presentacionDto=new PresentacionDto();
      this.tipoDto=new TipoDto();
      this.presentacionDto.tipoDto=this.tipoDto;

      this.presentacion=this.data.presentacion.nombre;
      this.presentacion_id=this.data.presentacion.id;
      this.tipo_id=this.data.presentacion.tipo_id;
      
      this.getAllTipos();
    }

    updatePresentacion(){
      this.presentacionDto.nombre=this.presentacion;
      this.presentacionDto.tipoDto.id=this.tipo_id;

      console.log(this.presentacionDto);

      this.eventBus.cast('inicio-progress','hola');
      this._adminPresentacionService.updatePresentacion(this.presentacion_id,this.presentacionDto).subscribe(
        (response)=>{
        console.log(response);
        if(response.estado=='success'){
            this._toastrService.success("Exito", "Subcategoria actualizada");
            this._toastrService.info('Espero un momento, por favor.','Actualizando...');
            this.eventBus.cast('actualizar-presentacion','actualizar');
        }
        else{
          this._toastrService.error("Esta presentacion ya se encuentra en el sistema", "Error");
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

    getAllTipos(){
      //this.dataSource.data=ELEMENT_DATA;
      this._adminTipoService.getAllTipos().subscribe(
        (response)=>{
          console.log(response);
          this.tipos=response.tipos;
          this.tipos_filtered=this.tipos.filter( tipo => tipo.estado === 'activo');
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
