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


  getEstudioPreguntas(id,id2): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this.http.get<Respuesta>(global.url + 'encuestado/pregunta-estudio/'+id+"/"+id2)
  }

  getPreguntas(_id): Observable<Respuesta> {

    return this.http.get<Respuesta>(global.url + 'admin/preguntas-categoria/1')
  }

}
