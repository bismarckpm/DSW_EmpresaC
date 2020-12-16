import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import {global} from 'src/urlGlobal';
import { nuevoEncuestado } from 'src/app/Entidades/nuevoEncuestado';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  constructor(private http: HttpClient) { }

  registro( nuevo: nuevoEncuestado): Observable<any> {
    return this.http.post(global.url+'registro', nuevo);
  }

  getAllPaises():Observable<any>{
    return this.http.get(global.url+'pais/all');
  }

  getAllEstados():Observable<any>{
    return this.http.get(global.url+'estado/all');
  }

  getAllCiudades():Observable<any>{
    return this.http.get(global.url+'ciudad/all');
  }

  getAllParroquias():Observable<any>{
    return this.http.get(global.url+'parroquia/all');
  }

  getAllNivelesAcademicos():Observable<any>{
    return this.http.get(global.url+'niveles_academicos/all');   
  }

  getAllMetodoConexion():Observable<any>{
    return this.http.get(global.url+'metodos_conexion/all');   
  }

  getAllOcupacion():Observable<any>{
    return this.http.get(global.url+'ocupaciones/all');   
  }



}
