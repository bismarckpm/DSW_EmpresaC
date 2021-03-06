import { Injectable } from '@angular/core';





import { delay, filter } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//Entidades 
import { Respuesta } from "../../Entidades/respuesta";
import { Pregunta } from "../../Entidades/pregunta";
import { PREGUNTAS } from "../../Entidades/DatosPrueba/preguntas";


//Base url
import { global } from "../../../urlGlobal";

@Injectable({
  providedIn: 'root'
})
export class PreguntaService {


  constructor(private http: HttpClient) { }


  opcion(){
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };
    return httpOptions
  }

  getPreguntas(_id): Observable<Respuesta> {

    return this.http.get<Respuesta>(global.url + 'admin/preguntas-categoria/'+_id, this.opcion())
  }

  postPreguntas(objeto:{}): Observable<Respuesta> {

  
    return this.http.put<Respuesta>(global.url+"admin/addPregunta",objeto, this.opcion() )
  }

  postEncuesta(id1,id2, objeto):Observable<Respuesta>{

    return this.http.put<Respuesta>(global.url+"admin/addEncuesta/"+id1+"/"+id2,objeto , this.opcion())
  }


}
