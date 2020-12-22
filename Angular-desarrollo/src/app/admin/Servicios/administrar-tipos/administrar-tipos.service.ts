import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarTiposService {

  constructor(public _http: HttpClient) { }

  getAllTipos():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'tipo/findall-tipos');
  }
  addTipo(tipoDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'tipo/add-tipo',tipoDto);
  }

  updateTipo(id,tipoDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'tipo/channge-tipo/'+id,tipoDto);
  }

  deleteTipo(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'tipo/delete-tipo/'+id);
  }
}
