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

//Base url
import { global } from "../../../urlGlobal";

@Injectable({
  providedIn: 'root'
})
export class SolicitudEstudioService {
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


  getEstudiosAdministrar(_id): Observable<Respuesta> {


    return this.http.get<Respuesta>(global.url + 'admin/estudios-asignados/'+_id, this.opcion())
  }

  getEstudiosPendientes(_id): Observable<Respuesta> {
    return this.http.get<Respuesta>(global.url + 'admin/estudios-no-asignados/'+_id, this.opcion())
    // 
  }

  DeleteEstudios(id:number): Observable<Respuesta> {
    
    return this.http.delete<Respuesta>(global.url+ 'admin/delete-solicitud/'+id, this.opcion())
    // 
  }

  getEstudio(id:number): Observable<Respuesta> {

    

    return this.http.get<Respuesta>(global.url + 'admin/estudio/'+id, this.opcion())
    
  }

  getParticipantes(id:number): Observable<Respuesta> {

    return this.http.get<Respuesta>(global.url + 'admin/estudios-participacion/'+id, this.opcion())
    
  }

  getEncuestados(id:number): Observable<any> {

    return this.http.get<any>(global.url + 'admin/sugerencia-participacion/'+id, this.opcion())
    
  }




}
