import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';
import { MarcaDto } from 'src/app/Entidades/marcaDto';

@Component({
  selector: 'app-anadir',
  templateUrl: './anadir.component.html',
  styleUrls: ['./anadir.component.css']
})
export class AnadirMarcaComponent implements OnInit {

  public marca:any;
  public marcaDto: MarcaDto; //Organizar mejor con las entidades
  selected = 'None';
  constructor(private _adminMarcaService:AdminMarcasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.marcaDto=new MarcaDto();
  }
  addMarca(){
    this.eventBus.cast('inicio-progress','hola');
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
        this.eventBus.cast('cerrar-marca-dialog','cerrar');
      });
  }
}
