import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from 'src/app/analista/servicios/consulta-estudios/consulta-estudios.service';

@Component({
  selector: 'app-graficos',
  templateUrl: './graficos.component.html',
  styleUrls: ['./graficos.component.css']
})
export class GraficosComponent implements OnInit {

  public estudio_id:any;
  public preguntas:any=[];
  public isLinear=false;

  //Configuracion para grafico
  title = 'Grafico de la pregunta';
  type = 'PieChart';

   columnNames = ['Option', 'Percentage'];
   options = {  
     is3D:true  
   };
   width = 550;
   height = 400;

  constructor(
    public dialogRef: MatDialogRef<GraficosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _consultaEstudios:ConsultaEstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.estudio_id=this.data.estudio;   
    this.getPreguntasEstudio(); //para probar con backend
  }

  getPreguntasEstudio(){
    console.log(this.estudio_id);
    this.eventBus.cast('inicio-progress','chao');
    this._consultaEstudios.getGraficos(this.estudio_id).subscribe(
      (response)=>{
        console.log(response);
        this.preguntas=response.preguntas;
        this._toastrService.success("Exito", "Todas los graficos");
        this.eventBus.cast('fin-progress','chao');
        
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');

        /*setTimeout(()=>{
          this.preguntas=JSON; 
        },2000);*/                //para probar sin backend 
        
      });
  }

}

const JSON=[
  {
    pregunta:"Pregunta 1",
    tipo_pregunta:"Opcion simple",
    resultados:[
      ['opcion 1', 25],
      ['opcion 2', 25],
      ['opcion 3', 25],
      ['opcion 4', 25],
    ]
  },
  {
    pregunta:"Pregunta 2",
    tipo_pregunta:"Opcion multiple",
    resultados:[
      ['opcion 1', 25],
      ['opcion 2', 25],
      ['opcion 3', 25],
      ['opcion 4', 25],
    ]
  },
  {
    pregunta:"Pregunta 3",
    tipo_pregunta:"booleana",
    resultados:[
      ['Si', 75],
      ['No', 25],
    ]
  },

];
