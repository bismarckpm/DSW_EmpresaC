import { Component, OnInit, ViewChild } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';


import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';



// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Pregunta } from "../../../Entidades/pregunta";
import { Estudio } from "../../../Entidades/estudio";


//Servicio 
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";
import { PreguntaService } from "../../Servicios/pregunta.service";
import { LoginService } from "../../../comun/servicios/login/login.service";


@Component({
  selector: 'app-asignar-encuesta',
  templateUrl: './asignar-encuesta.component.html',
  styleUrls: ['./asignar-encuesta.component.css']
})
export class AsignarEncuestaComponent implements OnInit {
  @ViewChild('fform') AgregadorFormDirective;
  AgregadorForm:FormGroup;
  encuestaForm:FormGroup;
  participanteForm:FormGroup;
  estudio:Estudio;
  preguntas:Pregunta[];
  preguntasSeleccionadas:Pregunta[];
  todasPreguntas:Pregunta[];
  preguntasNuevas:Pregunta[]=[];
  public admin_id:any

  participantes:any[];
  participantesSeleccionados:any[];


  error:string;

  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService,
    private fb: FormBuilder,
    private preguntaServicio:PreguntaService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) { 
      
      
      this.preguntasSeleccionadas=[];



        

    }

  ngOnInit(): void {
    this.participantesSeleccionados=[]
    this.CrearAgregador();
    this.CrearEncuesta();
    this.CrearParticipantes();
    this.init();

    this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
    .subscribe(x => { 
      console.log("entre "+ x)
      this.estudio = x.estudio

      this.solicitudServicio.getEncuestados(this.estudio.id).subscribe(x=>{
        this.participantes=x.Preguntas;
        console.log("participantes")
        console.log(this.participantes)
        this._toastrService.success("Exito", "toda la informacion del estudio");
        this.eventBus.cast('fin-progress','chao');

      }, error=>{
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

      })

      
      this.preguntaServicio.getPreguntas(this.estudio.caracteristicas.idcategoria).subscribe(x=>{
        this.todasPreguntas= x.Preguntas
        this.preguntas=x.Preguntas;
        console.log(this.preguntas)
        this._toastrService.success("Exito", "toda la informacion del estudio y las preguntas");
        this.eventBus.cast('fin-progress','chao');
        
        console.log(x.Preguntas)
        console.log(x)



      },
      error=>{
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
      }
      )


      
        },error=>{
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

  CrearParticipantes(){
    this.participanteForm=this.fb.group({
      id: 'none',

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

  AgregadorParticipante(){
    
    console.log(this.participanteForm.value);

    if(this.participanteForm.value.id!='none'){
      this.participantesSeleccionados.push(this.participantes.filter(x=>x.id==Number(this.participanteForm.value.id))[0])
      this.participantes=this.participantes.filter(x=>x.id!=Number(this.participanteForm.value.id))
      
      console.log(this.participantesSeleccionados)
    }
    this.participanteForm.reset({
      id: 'none'
    });

  }

  EliminarParticipante(id){
    this.participantes.push(this.participantesSeleccionados.filter(x=>x.id==id)[0])
    this.participantesSeleccionados=this.participantesSeleccionados.filter(x=>x.id!=id)

  }

  PreguntaNueva(nueva:Pregunta){

    this.preguntasSeleccionadas.push(nueva);
    console.log("Seleccionadas" + this.preguntasSeleccionadas)
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
      if(this.preguntasSeleccionadas.length==0 && this.participantesSeleccionados.length==0){
        this.error="selecciona al menos una pregunta y un participante"
      }
      else{
        var encuesta:{}
        var preguntas=[]
        var p:{}

        var par=[]
        var pa={}
        
        for (let index = 0; index < this.preguntasSeleccionadas.length; index++) {
          const element = this.preguntasSeleccionadas[index];
          p={"id":this.preguntasSeleccionadas[index].id}
          preguntas.push(p)
        }

        for (let index = 0; index < this.participantesSeleccionados.length; index++) {
          const element = this.participantesSeleccionados[index];
          pa={"id":this.participantesSeleccionados[index].id}
          par.push(pa)
        }

        encuesta={
          "nombre":this.encuestaForm.value.nombre,
          "preguntas":preguntas,
          "encuestado":par

        }




        console.log(encuesta)
        this.eventBus.cast('inicio-progress','hola');
        this.preguntaServicio.postEncuesta(this.estudio.caracteristicas.idMarca,this.estudio.id,encuesta).subscribe(x=>{

          this._toastrService.success("Exito", "Encuesta creada");
          this.eventBus.cast('fin-progress','chao');
          this.location.back();
          


        },error=>{
          console.log(error)
          if(error.error.estado=="unauthorized"){
            this.eventBus.cast('fin-progress','chao');
            this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
            this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
    
          }
          else{
            console.log(error);
            if(error.error.mensaje){
              this._toastrService.error("Ops! Hubo un problema.", error.error.mensaje)
            }
            else{
              this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
            }
            this.eventBus.cast('fin-progress','chao');
          }
        })


      }



    }
    else{
      this.error="coloque un nombre a la encuesta"
    }




  }

    init(){
    this.eventBus.cast('inicio-progress','hola');
    this.admin_id=localStorage.getItem('user_id');
   
  }

}
