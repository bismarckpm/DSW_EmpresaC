import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


export interface SolicitudEstudio {
  id:number;
}

@Component({
  selector: 'app-resultados',
  templateUrl: './resultados.component.html',
  styleUrls: ['./resultados.component.css']
})
export class ResultadosComponent implements OnInit {

  public estudio_id:any;
  constructor(
    public dialogRef: MatDialogRef<ResultadosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SolicitudEstudio) {}

  ngOnInit(): void {
  }

  doResultados():void{
    console.log(this.data.id);
  }

}
