import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-respuestas-persona',
  templateUrl: './respuestas-persona.component.html',
  styleUrls: ['./respuestas-persona.component.css']
})
export class RespuestasPersonaComponent implements OnInit {
  public participacion:any;
  public respuestas:any;
  public isLinear=false;

  constructor(
    public dialogRef: MatDialogRef<RespuestasPersonaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.participacion=this.data.participacion;
    this.respuestas=this.data.participacion.respuestas;
  }

}
