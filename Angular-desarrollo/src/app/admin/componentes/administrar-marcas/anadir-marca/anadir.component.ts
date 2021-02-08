import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';
import { MarcaDto } from 'src/app/Entidades/marcaDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import {AdministrarTiposService} from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';

import { LoginService } from "../../../../comun/servicios/login/login.service";

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
  public subcategoriaDto:SubcategoriaDto;
  public subcategorias:any[];
  public tipos:any[];
  public subcategorias_filtered: any;
  public tipos_filtered: any;

  constructor(private _adminMarcaService:AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _adminSubcategoriaService:AdministrarSubcategoriasService,
              private _adminTiposService:AdministrarTiposService,
              private loginService:LoginService
  ) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marcaDto=new MarcaDto();
    this.tipoDto=new TipoDto();
    this.tiposDto=new Array<TipoDto>();
    this.subcategoriaDto=new SubcategoriaDto();

    this.marcaDto.tipoDto=this.tiposDto;
    this.marcaDto.subcategoriaDto=this.subcategoriaDto;

    this.getAllSubcategorias();
    this.getAllTipos();
  }

  addMarca(){
    this.marcaDto.nombre=this.marca;
    this.marcaDto.subcategoriaDto.id=this.subcategoria_id;

    this.tipoDto.id=this.tipo_id;
    this.tiposDto.push(this.tipoDto);
    this.marcaDto.tipoDto=this.tiposDto;

    this.eventBus.cast('inicio-progress','hola');

    console.log(this.marcaDto);
  
    this._adminMarcaService.addMarca(this.marcaDto).subscribe(
      (response)=>{
        console.log(response);
        if(response.estado=='success'){
          this._toastrService.success("Exito", "Marca añadida");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-marca','actualizar');
        }else{
          this._toastrService.error("Esta marca ya se encuentra en el sistema", "Error");
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

  getAllSubcategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminSubcategoriaService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.subcategorias=response.subcategorias;
        this.subcategorias_filtered=this.subcategorias.filter( subcategoria => subcategoria.estado === 'activo');
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
    this._adminTiposService.getAllTipos().subscribe(
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
