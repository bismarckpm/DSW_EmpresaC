import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

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

  constructor() { }

  ngOnInit(): void {
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
}
