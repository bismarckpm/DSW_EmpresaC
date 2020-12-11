import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';



@Component({
  selector: 'app-administrar-categoria',
  templateUrl: './administrar-categoria.component.html',
  styleUrls: ['./administrar-categoria.component.css']
})
export class AdministrarCategoriaComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'estado', 'acciones'];
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
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, name: 'Hydrogen',  estado: 'H'},
  {id: 2, name: 'Helium',  estado: 'He'},
  {id: 3, name: 'Lithium',  estado: 'Li'},
  {id: 4, name: 'Beryllium',  estado: 'Be'},
  {id: 5, name: 'Boron',  estado: 'B'},
  {id: 1, name: 'Hydrogen',  estado: 'H'},
  {id: 2, name: 'Helium',  estado: 'He'},
 
  
];
  

