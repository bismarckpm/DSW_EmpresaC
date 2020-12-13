import { Component, OnInit, ViewChild } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';


import { FormBuilder, FormGroup, Validators } from '@angular/forms';



// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Pregunta } from "../../../Entidades/pregunta";
import { Estudio } from "../../../Entidades/estudio";


//Servicio 
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";
import { PreguntaService } from "../../Servicios/pregunta.service";


@Component({
  selector: 'app-asignar-encuesta',
  templateUrl: './asignar-encuesta.component.html',
  styleUrls: ['./asignar-encuesta.component.css']
})
export class AsignarEncuestaComponent implements OnInit {
  @ViewChild('fform') AgregadorFormDirective;
  AgregadorForm:FormGroup;
  encuestaForm:FormGroup;
  estudio:Estudio;
  preguntas:Pregunta[];
  preguntasSeleccionadas:Pregunta[];
  todasPreguntas:Pregunta[];
  preguntasNuevas:Pregunta[]=[];

  error:string;

  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService,
    private fb: FormBuilder,
    private preguntaServicio:PreguntaService) { 
      
      this.preguntasSeleccionadas=[];
      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
      .subscribe(x => { this.estudio = x.estudio; console.log(x)  });

      this.preguntaServicio.getPreguntas().subscribe(x=>{
        this.todasPreguntas= x.Preguntas
        this.preguntas=x.Preguntas;
        console.log(this.preguntas)
        
        console.log(x.Preguntas)
        console.log(x)
      })
    }

  ngOnInit(): void {
    this.CrearAgregador();
    this.CrearEncuesta()
  }


  CrearAgregador(){
    this.AgregadorForm=this.fb.group({
      tipo: 'none',

    });
  }

  CrearEncuesta(){
    this.encuestaForm=this.fb.group({
      nombre: '',

    });
  }

  Agregador(){
    console.log(this.AgregadorForm.value.tipo);
    if(this.AgregadorForm.value.tipo!='none'){
    this.preguntas=this.preguntas.filter(x=> x.id!=Number(this.AgregadorForm.value.tipo));
    this.preguntasSeleccionadas.push(this.todasPreguntas.filter(x=>x.id==Number(this.AgregadorForm.value.tipo))[0])
    console.log(this.preguntasSeleccionadas)
    }

    this.AgregadorForm.reset({
      tipo: 'none'
    });
  }

  PreguntaNueva(nueva:Pregunta){
    this.preguntasSeleccionadas.push(nueva);
  }

  Borrador(p:Pregunta){
    console.log(p);
    this.preguntas.push(p);
    this.preguntasSeleccionadas=this.preguntasSeleccionadas.filter(x=>x.id!=p.id);
  }


  aceptarEncuesta(){
    console.log(this.preguntasSeleccionadas)
    console.log(this.encuestaForm.value)
    if(this.encuestaForm.value.nombre!=""){
      if(this.preguntasSeleccionadas.length==0){
        this.error="seleccione 1 pregunta para la encuesta"
      }
      else{
        var encuesta:{}
        var preguntas=[]
        var p:{}
        
        for (let index = 0; index < this.preguntasSeleccionadas.length; index++) {
          const element = this.preguntasSeleccionadas[index];
          p={"id":this.preguntasSeleccionadas[index].id}
          preguntas.push(p)
        }

        encuesta={
          "nombre":this.encuestaForm.value.nombre,
          "preguntas":preguntas

        }

        console.log(encuesta)



      }



    }
    else{
      this.error="coloque un nombre a la encuesta"
    }




  }

}
