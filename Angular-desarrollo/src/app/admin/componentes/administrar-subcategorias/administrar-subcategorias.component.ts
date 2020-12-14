import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MatDialog } from '@angular/material/dialog';
import {  AdministrarSubcategoriasService } from "../../Servicios/administrar-subcategorias/administrar-subcategorias.service";
import { AnadirComponent } from './anadir/anadir.component';

export interface Subcategoria {
  id: number;
  nombre: string;
  categoria_id:number;
  categoria:string;
  estado: string;
  
}
const ELEMENT_DATA: Subcategoria[] = [
  {id: 1, nombre: 'Hydrogen',categoria_id:1, categoria:'Lacteos' , estado: 'H'},
];

@Component({
  selector: 'app-administrar-subcategorias',
  templateUrl: './administrar-subcategorias.component.html',
  styleUrls: ['./administrar-subcategorias.component.css']
})
export class AdministrarSubcategoriasComponent implements OnInit,AfterViewInit {
  public displayedColumns: string[] = ['id', 'nombre','categoria', 'estado', 'acciones'];
  public dataSource = new MatTableDataSource<Subcategoria>();
  public dialogRef;
  constructor(public dialog: MatDialog,private _adminSubcategoriaService:AdministrarSubcategoriasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.getAllSubcategorias();
    this.eventBus.cast('inicio-progress','hola');
  }

  getAllSubcategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminSubcategoriaService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.subcategorias;
        this._toastrService.success("Exito", "Todas las subcategorias");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  openDialog() {
    
    this.dialogRef = this.dialog.open(AnadirComponent, {
      width: '500px',
    });
    
    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
    
}

}