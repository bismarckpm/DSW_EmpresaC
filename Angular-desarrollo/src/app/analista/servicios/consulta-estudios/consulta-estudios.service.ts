import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

//entidades
import { Respuesta } from "../../../Entidades/respuesta";

@Injectable({
  providedIn: 'root'
})
export class ConsultaEstudiosService {

  constructor(public _http: HttpClient) { }

  getAllEstudios(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'analista/estudios/'+ _id);
  }

  getEstudiosTelefonicos(_id):Observable<Respuesta>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get<Respuesta>(global.url+'analista/estudios-telefono/'+ _id);
  }
}
