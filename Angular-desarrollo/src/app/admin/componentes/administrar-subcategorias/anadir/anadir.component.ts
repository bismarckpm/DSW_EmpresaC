import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';
import { AdministrarCategoriasService } from 'src/app/admin/Servicios/administrar-categorias/administrar-categorias.service';
import { CategoriaDto } from 'src/app/Entidades/categoriaDto';

@Component({
  selector: 'app-anadir',
  templateUrl: './anadir.component.html',
  styleUrls: ['./anadir.component.css']
})
export class AnadirComponent implements OnInit {
  public subcategoria:any;
  public categoria_id:any;
  public categorias:any[];
  public subcategoriasDto: SubcategoriaDto; 
  public categoriaDto:CategoriaDto;

  constructor(private _adminSubcategoriasService:AdministrarSubcategoriasService,
              private _adminCategoriaService: AdministrarCategoriasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) { }
  

  ngOnInit(): void {
    this.init();
  }


  init(){
    this.subcategoriasDto=new SubcategoriaDto();
    this.categoriaDto=new CategoriaDto();
    this.subcategoriasDto.categoriaDto=this.categoriaDto;
    this.getAllCategorias();
  }

  addSubCategorias(){
    this.eventBus.cast('inicio-progress','hola');
    this.subcategoriasDto.nombre=this.subcategoria;
    this.subcategoriasDto.categoriaDto.id=this.categoria_id;

    console.log(this.subcategoriasDto);
  
    this._adminSubcategoriasService.addSubcategorias(this.subcategoriasDto).subscribe(
      (response)=>{
        console.log(response);

        if(response.estado=='success'){
          this._toastrService.success("Exito", "Subcategoria añadida");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-subcategoria','actualizar');
        }
        else{
          this._toastrService.error("Esta subcategoria ya se encuentra en el sistema", "Error");
          this.eventBus.cast('fin-progress','chao');
        }

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-subcategoria-add','cerrar');
      });
  }

  getAllCategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminCategoriaService.getAllCategorias().subscribe(
      (response)=>{
        console.log(response);
        this.categorias=response.categorias;
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }

}







