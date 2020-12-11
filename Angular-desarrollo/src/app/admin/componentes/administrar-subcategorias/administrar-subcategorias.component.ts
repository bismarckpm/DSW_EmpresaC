import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-administrar-subcategorias',
  templateUrl: './administrar-subcategorias.component.html',
  styleUrls: ['./administrar-subcategorias.component.css']
})
export class AdministrarSubcategoriasComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name','categoria', 'estado', 'acciones'];
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
  categoria:string;
  estado: string;
  
}

const ELEMENT_DATA: PeriodicElement[] = [
  {id: 1, name: 'Hydrogen', categoria:' bebe',  estado: 'H'},
  {id: 2, name: 'Helium', categoria:' bebe', estado: 'He'},
  {id: 3, name: 'Lithium', categoria:' bebe', estado: 'Li'},
  {id: 4, name: 'Beryllium',  categoria:' bebe',estado: 'Be'},
  {id: 5, name: 'Boron', categoria:' bebe', estado: 'B'},
  {id: 1, name: 'Hydrogen',categoria:' bebe',  estado: 'H'},
  {id: 2, name: 'Helium', categoria:' bebe', estado: 'He'},
 
  
];
  
