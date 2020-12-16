import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class ConsultaEstudiosService {

  constructor(public _http: HttpClient) { }

  getAllEstudios(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'analista/estudios/'+ _id);
  }

  go(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'/analista/empezar-estudio/'+ _id);
  }

  eliminar_participacion(_id):Observable<any>{
      //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
      return this._http.get(global.url+'/analista/eliminar-participacion/'+ _id);
  }
}
