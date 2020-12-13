import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface Participacion {
  participacion_id:number;
  doc_id:number;
  usuario:string;
  correo:string;
  nombre:string;
  apellido:string;
  estado:string;
}

@Component({
  selector: 'app-muestra',
  templateUrl: './muestra.component.html',
  styleUrls: ['./muestra.component.css']
})
export class MuestraComponent implements OnInit {

  public participaciones:any[];
  constructor(
    public dialogRef: MatDialogRef<MuestraComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    console.log(this.data.participaciones);
    this.participaciones=this.data.participaciones;

  }

}
