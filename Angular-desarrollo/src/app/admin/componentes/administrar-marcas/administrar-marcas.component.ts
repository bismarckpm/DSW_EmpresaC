import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

export interface PeriodicElement {
  id: number;
  name: string;
  subcategoria: string;
  estado: string;
  
  
}
//datos de prueba -hace falta conectar con el back
const MARCAS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen', subcategoria: 'uno', estado: 'Activo'},
  {id:2, name: 'Helium', subcategoria: 'dos', estado: 'inactivo',},
  {id:3, name: 'Lithium', subcategoria: 'tres', estado: 'inactivo'},
  {id:4, name: 'Beryllium', subcategoria: 'cuatro', estado: 'activo'},
  {id:5, name: 'Boron', subcategoria: 'cinco', estado: 'activo'},
 
];

@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css']
})
export class AdministrarMarcasComponent implements OnInit, AfterViewInit{
  dataSource = new MatTableDataSource<PeriodicElement>(MARCAS);
  @ViewChild(MatPaginator) paginator: MatPaginator;
    
  constructor() { }

  ngOnInit(): void {
  }
  displayedColumns: string[] = ['id','name', 'subcategoria', 'estado'];
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
 


