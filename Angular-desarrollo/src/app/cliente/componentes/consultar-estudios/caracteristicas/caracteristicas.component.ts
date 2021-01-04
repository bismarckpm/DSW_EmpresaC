import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


export interface Caracteriticas {
  edad_min:number;
  edad_max:number;
  nivel_socioeconomico:string;
  nacionalidad:string;
  cantidad_hijos:number;
  genero:string;
  parroquia:string;
  ciudad: string;
  estado:string;
  pais:string;
  nivel_academico:string;
}

@Component({
  selector: 'app-caracteristicas',
  templateUrl: './caracteristicas.component.html',
  styleUrls: ['./caracteristicas.component.css']
})
export class CaracteristicasComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<CaracteristicasComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
  }

}
