import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MarcaDto } from 'src/app/Entidades/marcaDto';
import { TipoDto } from 'src/app/Entidades/TipoDto';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { AdministrarTiposService } from 'src/app/admin/Servicios/administrar-tipos/administrar-tipos.service';

@Component({
  selector: 'app-modificar-marca',
  templateUrl: './modificar-marca.component.html',
  styleUrls: ['./modificar-marca.component.css']
})
export class ModificarMarcaComponent implements OnInit {

  public marca:any;
  public subcategoria_id:any;
  public tipo_id:any;
  public marcaDto: MarcaDto;
  public tiposDto: TipoDto[];
  public tipoDto:TipoDto;
  public subcategorias: any[];
  public tipos:[];

  constructor(
              public dialogRef: MatDialogRef<ModificarMarcaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminMarcasService: AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _adminSubcategoriaService:AdministrarSubcategoriasService,
              private _adminTiposService:AdministrarTiposService
  ) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marca=this.data.marca.nombre;
    this.marcaDto=new MarcaDto();
    this.tipoDto=new TipoDto;
    this.tiposDto=new Array<TipoDto>();
    this.getAllSubcategorias();
    this.getAllTipos();
  }

  updateMarca(){
    this.marcaDto.nombre=this.marca;
    this.tipoDto.id=this.tipo_id;
    this.tiposDto.push(this.tipoDto);
    this.marcaDto.subcategoriaDto.id=this.subcategoria_id;

    console.log(this.marcaDto);
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
