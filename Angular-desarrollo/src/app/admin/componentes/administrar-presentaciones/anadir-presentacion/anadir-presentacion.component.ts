import { Component, OnInit } from '@angular/core';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarPresentacionService } from 'src/app/admin/Servicios/administrar-presentacion/administrar-presentacion.service';
import { PresentacionDto } from 'src/app/Entidades/presentacionDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';
import { AdministrarTiposService } from '../../../Servicios/administrar-tipos/administrar-tipos.service';
@Component({
  selector: 'app-anadir-presentacion',
  templateUrl: './anadir-presentacion.component.html',
  styleUrls: ['./anadir-presentacion.component.css']
})
export class AnadirPresentacionComponent implements OnInit {

  public presentacion:any;
  public tipo_id:any;
  public tipos:any[];
  public presentacionDto: PresentacionDto; 
  public tipoDto:TipoDto;
  public tipos_filtered: any;

  constructor(private _adminTipoService:AdministrarTiposService,
              private _adminPresentacionService: AdministrarPresentacionService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) { }
  

  ngOnInit(): void {
    this.init();
  }


  init(){
    this.presentacionDto=new PresentacionDto();
    this.tipoDto=new TipoDto();
    this.presentacionDto.tipoDto=this.tipoDto;
    this.getAllTipos();
  }

  addPresentacion(){
    this.eventBus.cast('inicio-progress','hola');
    this.presentacionDto.nombre=this.presentacion;
    this.presentacionDto.tipoDto.id=this.tipo_id;

    console.log(this.presentacionDto);
  
    this._adminPresentacionService.addPresentacion(this.presentacionDto).subscribe(
      (response)=>{
        console.log(response);

        if(response.estado=='success'){
          this._toastrService.success("Exito", "Presentacion añadida");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-presentacion','actualizar');
        }
        else{
          this._toastrService.error("Esta presentacion ya se encuentra en el sistema", "Error");
          this.eventBus.cast('fin-progress','chao');
        }

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-presentacion-add','cerrar');
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
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }

}
