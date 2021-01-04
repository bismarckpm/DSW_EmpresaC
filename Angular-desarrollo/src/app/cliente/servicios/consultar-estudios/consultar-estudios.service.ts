import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';
@Injectable({
  providedIn: 'root'
})
export class ConsultarEstudiosService {

  constructor(public _http: HttpClient) { }

  getAllEstudios(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'cliente/estudios/'+ _id);
  }

  

}

