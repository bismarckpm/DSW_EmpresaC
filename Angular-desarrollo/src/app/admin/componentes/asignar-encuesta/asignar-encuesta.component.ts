import { Component, OnInit, ViewChild } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';


import { FormBuilder, FormGroup, Validators } from '@angular/forms';



// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Pregunta } from "../../../Entidades/pregunta";
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

  estudio:SolicitudEstudio;
  preguntas:Pregunta[];
  preguntasSeleccionadas:Pregunta[];
  todasPreguntas:Pregunta[];
  preguntasNuevas:Pregunta[]=[];

  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService,
    private fb: FormBuilder,
    private preguntaServicio:PreguntaService) { 
      
      this.preguntasSeleccionadas=[];
      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
      .subscribe(x => { this.estudio = x;   });

      this.preguntaServicio.getPreguntas().subscribe(x=>{
        this.todasPreguntas=x
        this.preguntas=x;
      })
    }

  ngOnInit(): void {
    this.CrearAgregador();
  }


  CrearAgregador(){
    this.AgregadorForm=this.fb.group({
      tipo: 'none',

    });
  }

  Agregador(){
    console.log(this.AgregadorForm.value.tipo);
    if(this.AgregadorForm.value.tipo!='none'){
    this.preguntas=this.preguntas.filter(x=> x.Id!=Number(this.AgregadorForm.value.tipo));
    this.preguntasSeleccionadas.push(this.todasPreguntas.filter(x=>x.Id==Number(this.AgregadorForm.value.tipo))[0])
    console.log(this.preguntasSeleccionadas)
    }


    this.AgregadorForm.reset({
      tipo: 'none'
    });
  }

  Borrador(p:Pregunta){
    
    this.preguntas.push(p);
    this.preguntasSeleccionadas=this.preguntasSeleccionadas.filter(x=>x.Id!=p.Id);
  }

}
