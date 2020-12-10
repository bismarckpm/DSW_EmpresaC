import { Injectable } from '@angular/core';





import { delay, filter } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//Entidades 
import { Pregunta } from "../../Entidades/pregunta";
import { PREGUNTAS } from "../../Entidades/DatosPrueba/preguntas";


@Injectable({
  providedIn: 'root'
})
export class PreguntaService {
  URL:string="http://127.0.0.1:8080/pruebaORM-1.0-SNAPSHOT/api/"

  constructor(private http: HttpClient) { }

  getPreguntas(): Observable<Pregunta[]> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return of(PREGUNTAS).pipe(delay(2000));
  }

  postPreguntas(objeto:{}): Observable<{}> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this.http.put<{}>(this.URL+"admin/addPregunta",objeto)
  }


}
