import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';


@Injectable({
  providedIn: 'root'
})
export class AdminMarcasService {

  constructor(public _http: HttpClient) { }

  getAllMarcas():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'marca/all');
  }
}
