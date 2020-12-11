import {  AdminMarcasService } from "../../Servicios/administrar-marcas/admin-marcas.service";
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';


export interface PeriodicElement {
  id: number;
  nombre: string;
  subcategoria: string;
  estado: string;
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, nombre: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, nombre: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, nombre: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, nombre: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, nombre: 'Boron', subcategoria: 'estado', estado: 'B'},
  {id: 1, nombre: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, nombre: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, nombre: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, nombre: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, nombre: 'Boron', subcategoria: 'estado', estado: 'B'},
  {id: 1, nombre: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, nombre: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, nombre: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, nombre: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, nombre: 'Boron', subcategoria: 'estado', estado: 'B'},
  
];

@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css'],
  providers:[AdminMarcasService]
})
export class AdministrarMarcasComponent implements OnInit, AfterViewInit{
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  public displayedColumns: string[] = ['id', 'nombre', 'subcategoria', 'estado', 'acciones'];
  public dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  public marcas:any;
 

  constructor(private _adminMarcas:AdminMarcasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }
 
  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.getAllMarcas();
    this.eventBus.cast('inicio-progress','hola');
  }
  
  getAllMarcas(){
    this._adminMarcas.getAllMarcas().subscribe(
      (response)=>{
        console.log(response);
        this.marcas=response.marcas;
        this._toastrService.success("Exito", "Todas las marcas");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
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

  openDialog() {
    /*const dialogRef = this.dialog.open();

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });*/
  }
}


  

