import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarPresentacionService {

  constructor(public _http: HttpClient) { }

  opcion():any{
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };
    return httpOptions
  }

  getAllPresentaciones():Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'presentacion/findall-presentaciones', this.opcion());
  }

  addPresentacion(presentacionDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'presentacion/add-presentacion',presentacionDto, this.opcion());
  }

  updatePresentacion(id,presentacionDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'presentacion/channge-presentacion/'+id,presentacionDto, this.opcion());
  }

  deletePresentacion(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'presentacion/delete-presentacion/'+id, this.opcion());
  }

  activarPresentacion(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'presentacion/activar-presentacion/'+id, this.opcion());
  }

}


