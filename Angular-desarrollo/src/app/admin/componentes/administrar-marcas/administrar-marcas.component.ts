import { Component, OnInit } from '@angular/core';
import {  AdminMarcasService } from "../../Servicios/administrar-marcas/admin-marcas.service";
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';

@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css'],
  providers:[AdminMarcasService]
})
export class AdministrarMarcasComponent implements OnInit {

  public marcas:any;
 

  constructor(private _adminMarcas:AdminMarcasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {

    this.init();
  }

  displayedColumns: string[] = ['name', 'subcategoria', 'estado'];
 

  getAllMarcas(){
    this._adminMarcas.getAllMarcas().subscribe(
      (response)=>{
        console.log(response);
        this.marcas=response.marcas;
        this._toastrService.success("Exito", "Todas las marcas");
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }

  init(){
    this.getAllMarcas();
    this.prueba();
  }

  prueba(){
    this.marcas=[
      {id:1,nombre:'juana',subcategoria_id:2,estado:'activo'}
    ];
    this.actualizar();
  }

  actualizar(){
    setTimeout(()=>{
      this.marcas=[
        {id:1,nombre:'juana',subcategoria_id:2,estado:'activo'},
        {id:2,nombre:'Harina PAN',subcategoria_id:3,estado:'activo'},
      ];
    },3000);
  }
}
