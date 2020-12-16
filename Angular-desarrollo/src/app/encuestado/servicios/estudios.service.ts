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
export class EstudiosService {

  constructor(private http: HttpClient) { }


  getEstudiosPendiente(): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this.http.get<Respuesta>(global.url + 'encuestado/estudios-asignados/4')
  }



}
