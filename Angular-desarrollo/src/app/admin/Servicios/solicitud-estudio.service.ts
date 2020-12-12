import { Injectable } from '@angular/core';

import { SolicitudEstudio } from "../../Entidades/solicitudEstudio";


import { delay, filter } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//Solo para pruebas
import { SOLICITUDESESTUDIO } from "../../Entidades/DatosPrueba/SolicitudesEstudios";
import { SOLICITUDESPROGRESO } from "../../Entidades/DatosPrueba/SolicitudesProgreso";
import { Respuesta } from "../../Entidades/respuesta";
import { Estudio } from "../../Entidades/estudio";

@Injectable({
  providedIn: 'root'
})
export class SolicitudEstudioService {
  URL:string="http://127.0.0.1:8080/pruebaORM-1.0-SNAPSHOT/api/"
  res:{};
  constructor(private http: HttpClient) { }


  getEstudiosAdministrar(): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this.http.get<Respuesta>(this.URL + 'admin/estudios-asignados/20')
  }

  getEstudiosPendientes(): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => dish.featured)[0]).pipe(delay(2000));
    
    return this.http.get<Respuesta>(this.URL + 'admin/estudios-no-asignados/20')
    // 
  }

  DeleteEstudios(id:number): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => dish.featured)[0]).pipe(delay(2000));
    
    return this.http.delete<Respuesta>(this.URL + 'admin/delete-solicitud/'+id)
    // 
  }

  getEstudio(id:number): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => dish.featured)[0]).pipe(delay(2000));

    return this.http.get<Respuesta>(this.URL + 'admin/estudio/'+id)
    
  }


}
