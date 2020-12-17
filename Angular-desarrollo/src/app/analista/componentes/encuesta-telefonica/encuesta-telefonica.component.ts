import { Component, OnInit, ViewChild } from '@angular/core';

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';

import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';

//entidades
import { Pregunta } from "../../../Entidades/pregunta";
import { Opcion } from "../../../Entidades/opciones";

@Component({
  selector: 'app-encuesta-telefonica',
  templateUrl: './encuesta-telefonica.component.html',
  styleUrls: ['./encuesta-telefonica.component.css']
})
export class EncuestaTelefonicaComponent implements OnInit {
  isLinear = true;
  encuestaForm:FormGroup;
  preguntas:Pregunta[]
  public encuestado_id:any
  opciones:Opcion[];
  estudioid:number;

  @ViewChild('stepper') stepper;

  constructor(private fb: FormBuilder,
    
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.crearFormulario();
  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.encuestado_id=localStorage.getItem('user_id');
   
  }

  crearFormulario(){

    for (let index = 0; index < this.preguntas.length; index++) {
     this.addPregunta();
     if(this.preguntas[index].tipopregunta=="Opcion multiple"){
      for (let l = 0; l < this.preguntas[index].opciones.length; l++) {
        this.opciones.push(this.preguntas[index].opciones[l])
        
      }

     }
      
    }
    this.falseOpciones();
    console.log(this.opciones)

  }

  CrearInicial(){
    this.encuestaForm=this.fb.group({
      preguntas:this.fb.array([]),

      
    });
  }

  CrearPregunta():FormGroup{
    return this.fb.group({
      pregunta: ['',[Validators.required]],


    });
  }

  get Preguntas(){
    return this.encuestaForm.get("preguntas") as FormArray;
    
  }



  addPregunta(){
    this.Preguntas.push(this.CrearPregunta());
  }

  falseOpciones(){
    for (let index = 0; index < this.opciones.length; index++) {
      this.opciones[index].isSelec=false;
      
    }
  }


}
