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
  nombre: string;
  categoria:string;
  estado: string;
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, nombre: 'Hydrogen', categoria:' bebe',  estado: 'H'},
  {id: 2, nombre: 'Helium', categoria:' bebe', estado: 'He'},
  {id: 3, nombre: 'Lithium', categoria:' bebe', estado: 'Li'},
  {id: 4, nombre: 'Beryllium',  categoria:' bebe',estado: 'Be'},
  {id: 5, nombre: 'Boron', categoria:' bebe', estado: 'B'},
  {id: 1, nombre: 'Hydrogen',categoria:' bebe',  estado: 'H'},
  {id: 2, nombre: 'Helium', categoria:' bebe', estado: 'He'},
 
];

@Component({
  selector: 'app-administrar-subcategorias',
  templateUrl: './administrar-subcategorias.component.html',
  styleUrls: ['./administrar-subcategorias.component.css']
})
export class AdministrarSubcategoriasComponent implements OnInit,AfterViewInit {
  public displayedColumns: string[] = ['id', 'nombre','categoria', 'estado', 'acciones'];
  public dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  public subcategorias:any[];

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


  
