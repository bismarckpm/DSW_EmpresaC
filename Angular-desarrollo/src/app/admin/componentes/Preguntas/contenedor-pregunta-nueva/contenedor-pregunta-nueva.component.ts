import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

//Entidades
import { Pregunta } from "../../../../Entidades/pregunta";

@Component({
  selector: 'app-contenedor-pregunta-nueva',
  templateUrl: './contenedor-pregunta-nueva.component.html',
  styleUrls: ['./contenedor-pregunta-nueva.component.css']
})
export class ContenedorPreguntaNuevaComponent implements OnInit {
  @ViewChild('fform') PreguntaFormDirective;
  PreguntaForm:FormGroup;
  @Input() id:Number;
  pregunta:Pregunta;
  tipo:String="Texto";

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.CrearAgregador();
  }

  CrearAgregador(){
    this.PreguntaForm=this.fb.group({
      pregunta: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(25)] ],
      tipo: ['Texto']
    });

    this.PreguntaForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

  }
  onValueChanged(data?: any){
    this.tipo=this.PreguntaForm.value.tipo
    console.log(this.tipo);
  }
  


}
