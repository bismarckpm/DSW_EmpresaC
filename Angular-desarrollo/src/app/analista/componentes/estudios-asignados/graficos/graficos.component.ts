import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from 'src/app/analista/servicios/consulta-estudios/consulta-estudios.service';

import { LoginService } from "../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-graficos',
  templateUrl: './graficos.component.html',
  styleUrls: ['./graficos.component.css']
})
export class GraficosComponent implements OnInit {

  public estudio_id:any;
  public preguntas:any=[];
  public isLinear=false;

  //Configuracion para grafico de torta
  title = 'Grafico de la pregunta';
  type = 'PieChart';
  columns = ['Option', 'Percentage'];
  options = {  
    is3D:true  
  };
  width = 550;
  height = 400;

  //Configuracion para grafico de barras
  typebar = 'BarChart';
  widthbar = 500;
  optionsbar = { };
  columnsbar = ['Option', 'Total'];

  constructor(
    public dialogRef: MatDialogRef<GraficosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _consultaEstudios:ConsultaEstudiosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) {}

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
        this.preguntas=response.Preguntas;
        this._toastrService.success("Exito", "Todas los graficos");
        this.eventBus.cast('fin-progress','chao');
        
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          this.eventBus.cast('fin-progress','chao');
        }
        
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
      ['opcion 4', 26],
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
