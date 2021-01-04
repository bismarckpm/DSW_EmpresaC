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
  selector: 'app-responder',
  templateUrl: './responder.component.html',
  styleUrls: ['./responder.component.css']
})
export class ResponderComponent implements OnInit {

  public participaciones:any[];
  constructor(
    public dialogRef: MatDialogRef<ResponderComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    console.log(this.data.participaciones);
    this.participaciones=this.data.participaciones;

  }

  cerrarModal(){
    this.dialogRef.close();
  }

}
