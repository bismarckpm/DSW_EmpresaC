import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from 'src/app/analista/servicios/consulta-estudios/consulta-estudios.service';
import { RespuestasPersonaComponent } from './respuestas-persona/respuestas-persona.component';

@Component({
  selector: 'app-individual',
  templateUrl: './individual.component.html',
  styleUrls: ['./individual.component.css']
})
export class IndividualComponent implements OnInit {

  public estudio_id:any;
  public participaciones:any[];
  public dialogRef2;

  constructor(
    public dialogRef: MatDialogRef<IndividualComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _consultaEstudios:ConsultaEstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.estudio_id=this.data.estudio;
    this.getRespuestasEstudio();
    //this.participaciones=PARTIPACIONES;
  }

  getRespuestasEstudio(){
    console.log(this.estudio_id);
    
    this._consultaEstudios.getRespuestasEstudio(this.estudio_id).subscribe(
      (response)=>{
        console.log(response);
        this.participaciones=response.participaciones;
        this._toastrService.success("Exito", "Todas las participaciones realizadas");
        this.eventBus.cast('fin-progress','chao');
        
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  ver(participacion): void {
    console.log(participacion.respuestas);
    this.dialogRef2 = this.dialog.open(RespuestasPersonaComponent, {
      width: '600px',
      height:'500px',
      //data:{participaciones:MUESTRA}  para probar
      data:{participacion:participacion},
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}

const PARTIPACIONES=[
  {
    participacion_id:12,
    encuestado:'Juan Perez',
    usuario:'encuestado99',
    respuestas:[
      {
        pregunta:"que dia es hoy",
        tipo_pregunta:"Opcion multiple",
        respuesta:[
          {
            respuestaOpcion:"a"
          },
          {
            respuestaOpcion:"b"
          }
        ]
      },
      {
        pregunta:"que dia era ayer",
        tipo_pregunta:"rango",
        respuesta:[
          {
            respuestaOpcion:2
          }
        ]
      },
      {
        pregunta:"que dia es mañana",
        tipo_pregunta:"Opcion simple",
        respuesta:[
          {
            respuestaOpcion:"a"
          },
        ]
      },
      {
        pregunta:"verdad o falso",
        tipo_pregunta:"booleana",
        respuesta:[
          {
            respuestaOpcion:"verdad"
          },
        ]
      },
      {
        pregunta:"que dia era ayer",
        tipo_pregunta:"rango",
        respuesta:[
          {
            respuestaOpcion:2
          }
        ]
      },
      {
        pregunta:"que dia es mañana",
        tipo_pregunta:"Opcion simple",
        respuesta:[
          {
            respuestaOpcion:"a"
          },
        ]
      },
      {
        pregunta:"verdad o falso",
        tipo_pregunta:"booleana",
        respuesta:[
          {
            respuestaOpcion:"verdad"
          },
        ]
      }

    ]
  }
];
