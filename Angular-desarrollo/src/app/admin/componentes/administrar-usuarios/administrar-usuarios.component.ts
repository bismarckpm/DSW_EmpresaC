import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

export interface PeriodicElement {
  id: number;
  name: string;
  estado: string;
  rol: string;
  
  
}
//datos de prueba -hace falta conectar con el back
const USUARIOS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen', estado: 'Activo',rol: 'admin'},
  {id:2, name: 'Helium',  estado: 'inactivo', rol:'admin'},
  {id:3, name: 'Lithium',  estado: 'inactivo',rol:'admin'},
  {id:4, name: 'Beryllium',  estado: 'activo',rol:'admin'},
  {id:5, name: 'Boron',  estado: 'activo',rol:'admin'}
]
@Component({
  selector: 'app-administrar-usuarios',
  templateUrl: './administrar-usuarios.component.html',
  styleUrls: ['./administrar-usuarios.component.css']
})
export class AdministrarUsuariosComponent implements OnInit {
  dataSource = new MatTableDataSource<PeriodicElement>(USUARIOS);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor() { }

  ngOnInit(): void {
  }
  displayedColumns: string[] = ['id','name', 'estado','rol'];
  //dataSource = MARCAS;
 
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue.trim().toLowerCase();

    
    }
}
