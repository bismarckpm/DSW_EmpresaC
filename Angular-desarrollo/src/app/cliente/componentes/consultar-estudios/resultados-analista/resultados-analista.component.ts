import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from 'src/app/analista/servicios/consulta-estudios/consulta-estudios.service';

@Component({
  selector: 'app-resultados-analista',
  templateUrl: './resultados-analista.component.html',
  styleUrls: ['./resultados-analista.component.css']
})
export class ResultadosAnalistaComponent implements OnInit {
  public estudio_id: any;
  public resultado: any='';

  constructor(
    public dialogRef: MatDialogRef<ResultadosAnalistaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _consultaEstudios:ConsultaEstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus
  ) { }

  ngOnInit(): void {
    this.estudio_id = this.data.estudio;
  }

  getResult() {
    console.log(this.estudio_id);
    this.eventBus.cast('inicio-progress','chao');
    this._consultaEstudios.getRespuestaAnalista(this.estudio_id).subscribe(
      (response)=>{
        console.log(response);
        this.resultado=response.Preguntas;
        this._toastrService.success("Exito", "Todas los graficos");
        this.eventBus.cast('fin-progress','chao');
        
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        
      });
  }

}
