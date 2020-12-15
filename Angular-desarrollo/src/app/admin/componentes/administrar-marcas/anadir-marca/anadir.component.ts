import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';
import { MarcaDto } from 'src/app/Entidades/marcaDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import {AdministrarTiposService} from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';

@Component({
  selector: 'app-anadir',
  templateUrl: './anadir.component.html',
  styleUrls: ['./anadir.component.css']
})
export class AnadirMarcaComponent implements OnInit {

  public marca:any;
  public subcategoria_id:any;
  public tipo_id:any;
  public marcaDto: MarcaDto;
  public tiposDto: TipoDto[];
  public tipoDto:TipoDto;
  public subcategorias:any[];
  public tipos:any[];

  constructor(private _adminMarcaService:AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _adminSubcategoriaService:AdministrarSubcategoriasService,
              private _adminTiposService:AdministrarTiposService
  ) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marcaDto=new MarcaDto();
    this.getAllSubcategorias();
    this.getAllTipos();
  }

  addMarca(){
    this.marcaDto.nombre=this.marca;
    this.tipoDto.id=this.tipo_id;
    this.tiposDto.push(this.tipoDto);
    this.marcaDto.subcategoriaDto.id=this.subcategoria_id;

    /*this.eventBus.cast('inicio-progress','hola');
    this.marcaDto.id=this.marca;
    console.log(this.marcaDto);
  
    this._adminMarcaService.addMarca(this.marcaDto).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Marca añadida");
        this._toastrService.info('Espero un momento, por favor.','Actualizando...');
        this.eventBus.cast('actualizar','actualizar');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-marca-add','cerrar');
      });*/
  }

  getAllSubcategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminSubcategoriaService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.subcategorias=response.subcategorias;
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }

  getAllTipos(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminTiposService.getAllTipos().subscribe(
      (response)=>{
        console.log(response);
        this.tipos=response.tipos;
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }

}
