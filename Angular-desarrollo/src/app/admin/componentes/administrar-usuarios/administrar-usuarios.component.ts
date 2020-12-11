import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-administrar-usuarios',
  templateUrl: './administrar-usuarios.component.html',
  styleUrls: ['./administrar-usuarios.component.css']
})
export class AdministrarUsuariosComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'rol', 'estado', 'acciones'];
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
  estado: string;
  rol: string;
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, name: 'Hydrogen', rol: 'admin', estado: 'H'},
  {id: 2, name: 'Helium', rol: 'admin', estado: 'He'},
  {id: 3, name: 'Lithium', rol: 'admin', estado: 'Li'},
  {id: 4, name: 'Beryllium', rol: 'analista', estado: 'Be'},
  {id: 5, name: 'Boron', rol: 'encuestado', estado: 'B'},
  {id: 1, name: 'Hydrogen', rol: 'admin', estado: 'H'},
 
  
  
];
  
