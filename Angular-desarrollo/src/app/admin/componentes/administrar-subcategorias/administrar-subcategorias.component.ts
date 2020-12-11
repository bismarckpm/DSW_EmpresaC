import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MatDialog } from '@angular/material/dialog';
import {  AdministrarSubcategoriasService } from "../../Servicios/administrar-subcategorias/administrar-subcategorias.service";

export interface PeriodicElement {
  id: number;
  name: string;
  categoria: string;
  estado: string;
  
}
const SUBCATEGORIAS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen', categoria: 'uno', estado: 'Activo'},
  {id:2, name: 'Helium', categoria: 'dos', estado: 'inactivo',},
  {id:3, name: 'Lithium', categoria: 'tres', estado: 'inactivo'},
  {id:4, name: 'Beryllium', categoria: 'cuatro', estado: 'activo'},
  {id:5, name: 'Boron', categoria: 'cinco', estado: 'activo'},
 
];

@Component({
  selector: 'app-administrar-subcategorias',
  templateUrl: './administrar-subcategorias.component.html',
  styleUrls: ['./administrar-subcategorias.component.css']
})
export class AdministrarSubcategoriasComponent implements OnInit {
  dataSource = new MatTableDataSource<PeriodicElement>(SUBCATEGORIAS);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  public subcategorias:any[];

  constructor(public dialog: MatDialog,private _adminSubcategoriaService:AdministrarSubcategoriasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    //this.init();
    this.getAllSubcategorias();
  }
  displayedColumns: string[] = ['id','name', 'categoria', 'estado'];
  //dataSource = SUBCATGORIAS;
 
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
    this._adminSubcategoriaService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.subcategorias=response.subcategorias;
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
    /*const dialogRef = this.dialog.open();

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });*/
  }
}
