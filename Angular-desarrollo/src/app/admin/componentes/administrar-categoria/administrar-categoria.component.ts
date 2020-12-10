import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

export interface PeriodicElement {
  id: number;
  name: string;
  estado: string;
  
  
}
const CATEGORIAS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen',  estado: 'Activo'},
  {id:2, name: 'Helium',  estado: 'inactivo',},
  {id:3, name: 'Lithium',  estado: 'inactivo'},
  {id:4, name: 'Beryllium',  estado: 'activo'},
  {id:5, name: 'Boron',  estado: 'activo'},
 
];

@Component({
  selector: 'app-administrar-categoria',
  templateUrl: './administrar-categoria.component.html',
  styleUrls: ['./administrar-categoria.component.css']
})
export class AdministrarCategoriaComponent implements OnInit {
  dataSource = new MatTableDataSource<PeriodicElement>(CATEGORIAS);
  @ViewChild(MatPaginator) paginator: MatPaginator;
    

  constructor() { }

  ngOnInit(): void {
  }
  displayedColumns: string[] = ['id','name', 'estado'];
  //dataSource = CATEGORIAS;
 
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue.trim().toLowerCase();

    
    }
}
