import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';

@Component({
  selector: 'app-modif-subcategoria',
  templateUrl: './modif-subcategoria.component.html',
  styleUrls: ['./modif-subcategoria.component.css']
})
export class ModifSubcategoriaComponent implements OnInit {
  selected = 'option2';
  public subcategoria:any;
  public subcategoriasDto: SubcategoriaDto; 
  constructor(private _adminSubcategoriasService:AdministrarSubcategoriasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.subcategoriasDto=new SubcategoriaDto();
  }

  addSubCategorias(){
    this.eventBus.cast('inicio-progress','hola');
    this.subcategoriasDto.nombre=this.subcategoria;
    console.log(this.subcategoriasDto);
  
    this._adminSubcategoriasService.addSubcategorias(this.subcategoriasDto).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Categoria añadida");
        this._toastrService.info('Espero un momento, por favor.','Actualizando...');
        this.eventBus.cast('actualizar','actualizar');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-categoria-dialog','cerrar');
      });
  }

}