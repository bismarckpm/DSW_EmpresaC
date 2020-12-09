import { Component, OnInit } from '@angular/core';
export interface PeriodicElement {
  id: number;
  name: string;
  subcategoria: string;
  estado: string;
  
}
//datos de prueba -hace falta conectar con el back
const MARCAS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen', subcategoria: 'uno', estado: 'Activo'},
  {id:2, name: 'Helium', subcategoria: 'dos', estado: 'inactivo'},
  {id:3, name: 'Lithium', subcategoria: 'tres', estado: 'inactivo'},
  {id:4, name: 'Beryllium', subcategoria: 'cuatro', estado: 'activo'},
  {id:5, name: 'Boron', subcategoria: 'cinco', estado: 'activo'},
 
];

@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css']
})
export class AdministrarMarcasComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  displayedColumns: string[] = ['name', 'subcategoria', 'estado'];
  dataSource = MARCAS;



}
