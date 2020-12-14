import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdministrarSubcategoriasService } from 'src/app/admin/Servicios/administrar-subcategorias/administrar-subcategorias.service';
import { SubcategoriaDto } from 'src/app/Entidades/subcategoriaDto';

@Component({
  selector: 'app-anadir',
  templateUrl: './anadir.component.html',
  styleUrls: ['./anadir.component.css']
})
export class AnadirComponent implements OnInit {
  public subcategoria:any;
  public subcategoriasDto: SubcategoriaDto; //Organiz
  selected = 'option2';
 
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







