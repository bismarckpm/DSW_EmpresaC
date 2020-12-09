import { Injectable } from '@angular/core';

import { SolicitudEstudio } from "../../Entidades/solicitudEstudio";


import { delay, filter } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

//Solo para pruebas
import { SOLICITUDESESTUDIO } from "../../Entidades/DatosPrueba/SolicitudesEstudios";
import { SOLICITUDESPROGRESO } from "../../Entidades/DatosPrueba/SolicitudesProgreso";

@Injectable({
  providedIn: 'root'
})
export class SolicitudEstudioService {
  URL:string="http://127.0.0.1:8080/pruebaORM-1.0-SNAPSHOT/api/"
  res:{};
  constructor(private http: HttpClient) { }


  getEstudiosAdministrar(): Observable<SolicitudEstudio[]> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return of(SOLICITUDESPROGRESO).pipe(delay(2000));
  }

  getEstudiosAdministrar2(): Observable<SolicitudEstudio[]> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
     return this.http.get<any>(this.URL + 'admin/estudios-asignados')
     
  }

  getEstudiosPendientes(): Observable<SolicitudEstudio[]> {
    // return of(DISHES.filter((dish) => dish.featured)[0]).pipe(delay(2000));
    return of(SOLICITUDESESTUDIO).pipe(delay(2000));
    // 
  }

  getEstudio(id:number): Observable<SolicitudEstudio> {
    // return of(DISHES.filter((dish) => dish.featured)[0]).pipe(delay(2000));
    return of(SOLICITUDESESTUDIO.filter(x=> x.id==id)[0]).pipe(delay(2000));
  }


}
