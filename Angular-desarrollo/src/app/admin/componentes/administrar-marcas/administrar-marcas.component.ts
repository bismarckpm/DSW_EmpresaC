import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css']
})
export class AdministrarMarcasComponent implements OnInit, AfterViewInit{
  displayedColumns: string[] = ['id', 'name', 'subcategoria', 'estado', 'acciones'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}

export interface PeriodicElement {
  id: number;
  name: string;
  subcategoria: string;
  estado: string;
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, name: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, name: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, name: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, name: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, name: 'Boron', subcategoria: 'estado', estado: 'B'},
  {id: 1, name: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, name: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, name: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, name: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, name: 'Boron', subcategoria: 'estado', estado: 'B'},
  {id: 1, name: 'Hydrogen', subcategoria: 'hola', estado: 'H'},
  {id: 2, name: 'Helium', subcategoria: 'cocala', estado: 'He'},
  {id: 3, name: 'Lithium', subcategoria: 'bebe', estado: 'Li'},
  {id: 4, name: 'Beryllium', subcategoria: 'como', estado: 'Be'},
  {id: 5, name: 'Boron', subcategoria: 'estado', estado: 'B'},
  
  
  
];
  



