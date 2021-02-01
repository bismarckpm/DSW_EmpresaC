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

  opcion(){
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };
    return httpOptions
  }


  getEstudiosPendiente(id): Observable<Respuesta> {
    return this.http.get<Respuesta>(global.url + 'encuestado/estudios-asignados/'+id, this.opcion())
  }

  getEstudio(id:number): Observable<Respuesta> {
    return this.http.get<Respuesta>(global.url + 'admin/estudio/'+id, this.opcion())
  }

  getEncuestadoId(user_id): Observable<any> {

    return this.http.get<Respuesta>(global.url + 'encuestado/get-id/'+user_id, this.opcion())
  }



}
