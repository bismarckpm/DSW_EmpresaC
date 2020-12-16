import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from 'src/app/analista/servicios/consulta-estudios/consulta-estudios.service';

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
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _consultaEstudios:ConsultaEstudiosService,private _toastrService: ToastrService,private eventBus: NgEventBus) {}

  ngOnInit(): void {
    console.log(this.data.participaciones);
    this.participaciones=this.data.participaciones;

  }

  eliminar_participacion(id){
    this.eventBus.cast('inicio-progress','hola');
    this._consultaEstudios.eliminar_participacion(id).subscribe(
      (response)=>{
        console.log(response);
        if(response=='success'){
          this._toastrService.success("Exito", "La participacion se ha eliminado");
          this.eventBus.cast('actualizar','actualizar');
        }
        else{
          this._toastrService.success("Error", "Un error ha ocurrido");
          this.eventBus.cast('cerrar-modal-analista','cerrar');
        }

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-modal-analista','cerrar');
      });
  }
}
