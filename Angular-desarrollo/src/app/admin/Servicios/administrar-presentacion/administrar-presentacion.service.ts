import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarPresentacionService {

  constructor(public _http: HttpClient) { }

  getAllPresentaciones():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'presentacion/findall-presentaciones');
  }

  addPresentacion(presentacionDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'presentacion/add-presentacion',presentacionDto);
  }

  updatePresentacion(id,presentacionDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'presentacion/channge-presentacion/'+id,presentacionDto);
  }

  deletePresentacion(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'presentacion/delete-presentacion/'+id);
  }

}


