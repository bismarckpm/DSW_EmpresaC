import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MarcaDto } from 'src/app/Entidades/marcaDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { AdministrarTiposService } from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';
import { MarcaTipoDto } from 'src/app/Entidades/MarcaTipoDto';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-modificar-marca',
  templateUrl: './modificar-marca.component.html',
  styleUrls: ['./modificar-marca.component.css']
})
export class ModificarMarcaComponent implements OnInit {

  public marca:any;
  public marca_id:any;
  public subcategoria_id:any;
  public tipo_id:any;
  public marcaDto: MarcaDto;
  public tiposDto: TipoDto[];
  public tipoDto:TipoDto;
  public marcaTipoDto:MarcaTipoDto;
  public subcategoriaDto:SubcategoriaDto;
  public subcategorias: any[];
  public tipos:any[];
  public subcategorias_filtered: any;
  public tipos_filtered: any;

  constructor(
              public dialogRef: MatDialogRef<ModificarMarcaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminMarcasService: AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _adminSubcategoriaService:AdministrarSubcategoriasService,
              private _adminTiposService:AdministrarTiposService,
              private loginService:LoginService
  ) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marca=this.data.marca.nombre;
    this.marca_id=this.data.marca.id;
    this.tipo_id=this.data.marca.tipos[0].tipo_id;
    this.subcategoria_id=this.data.marca.subcategoria_id;
    this.marcaDto=new MarcaDto();
    this.tipoDto=new TipoDto();
    this.tiposDto=new Array<TipoDto>();
    this.marcaTipoDto=new MarcaTipoDto();
    this.subcategoriaDto=new SubcategoriaDto();

    this.marcaDto.subcategoriaDto=this.subcategoriaDto;
    this.marcaDto.tipoDto=this.tiposDto;
    this.marcaDto.marcaTipoDto=this.marcaTipoDto;
    
    this.getAllSubcategorias();
    this.getAllTipos();
  }

  updateMarca(){
    this.marcaDto.nombre=this.marca;
    this.marcaDto.subcategoriaDto.id=this.subcategoria_id;
    this.marcaDto.marcaTipoDto.id=this.data.marca.tipos[0].marca_tipo_id;

    this.tipoDto.id=this.tipo_id;
    this.tiposDto.push(this.tipoDto);
    this.marcaDto.tipoDto=this.tiposDto;

    this.eventBus.cast('inicio-progress','hola');

    console.log(this.marcaDto);
  
    this._adminMarcasService.updateMarca(this.marca_id,this.marcaDto).subscribe(
      (response)=>{
        console.log(response);
        if(response.estado=='success'){
          this._toastrService.success("Exito", "Marca actualizada");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-marca','actualizar');
        }
        else{
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
