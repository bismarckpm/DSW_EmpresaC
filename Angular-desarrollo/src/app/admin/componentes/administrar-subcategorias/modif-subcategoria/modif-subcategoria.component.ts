  
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';
import { AdministrarCategoriasService } from 'src/app/admin/Servicios/administrar-categorias/administrar-categorias.service';
import { CategoriaDto } from 'src/app/Entidades/categoriaDto';

@Component({
  selector: 'app-modif-subcategoria',
  templateUrl: './modif-subcategoria.component.html',
  styleUrls: ['./modif-subcategoria.component.css']
})
export class ModifSubcategoriaComponent implements OnInit {
  public subcategoria:any;
  public categoria_id:any;
  public subcategoria_id:any;
  public categorias:any[];
  public subcategoriaDto: SubcategoriaDto; 
  public categoriaDto: CategoriaDto;

    constructor(
                public dialogRef: MatDialogRef<ModifSubcategoriaComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                private _adminSubcategoriasService:AdministrarSubcategoriasService,
                private _adminCategoriaService: AdministrarCategoriasService, 
                private _toastrService: ToastrService,
                private eventBus: NgEventBus
    ) {}

    ngOnInit(): void {
      this.init();
    }

    init(){
      this.subcategoriaDto=new SubcategoriaDto();
      this.categoriaDto=new CategoriaDto();
      this.subcategoriaDto.categoriaDto=this.categoriaDto;

      this.subcategoria=this.data.subcategoria.nombre;
      this.subcategoria_id=this.data.subcategoria.id;
      this.categoria_id=this.data.subcategoria.categoria_id;
      
      this.getAllCategorias();
    }

    updateSubCategorias(){
      this.subcategoriaDto.nombre=this.subcategoria;
      this.subcategoriaDto.categoriaDto.id=this.categoria_id;

      console.log(this.subcategoriaDto);

      this.eventBus.cast('inicio-progress','hola');
      this._adminSubcategoriasService.updateSubcategoria(this.subcategoria_id,this.subcategoriaDto).subscribe(
        (response)=>{
        console.log(response);
        if(response.estado=='success'){
            this._toastrService.success("Exito", "Subcategoria actualizada");
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