import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//Entidades
import { Respuesta } from "../../Entidades/respuesta";

//URL base
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


  getEstudioPreguntas(id,id2): Observable<Respuesta> {

    return this.http.get<Respuesta>(global.url + 'encuestado/pregunta-estudio/'+id+"/"+id2 , this.opcion())
  }

  getPreguntas(_id): Observable<Respuesta> {

    return this.http.get<Respuesta>(global.url + 'admin/preguntas-categoria/1' , this.opcion())
  }

  Responder(id1, id2,id3, objeto): Observable<Respuesta> {


    return this.http.put<Respuesta>(global.url+"encuestado/Respuesta/"+id1+"/"+id2+"/"+id3,objeto , this.opcion())
  }

  cerrarParticipacion(id,id2): Observable<Respuesta> {
    
    return this.http.delete<Respuesta>(global.url + 'encuestado/finalizar/'+id+"/"+id2 , this.opcion())
  }



}
