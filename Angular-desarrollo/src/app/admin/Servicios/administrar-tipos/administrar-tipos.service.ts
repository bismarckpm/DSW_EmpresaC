import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarTiposService {

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

  getAllTipos():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'tipo/findall-tipos', this.opcion());
  }
  addTipo(tipoDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'tipo/add-tipo',tipoDto, this.opcion());
  }

  updateTipo(id,tipoDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'tipo/channge-tipo/'+id,tipoDto, this.opcion());
  }

  deleteTipo(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'tipo/delete-tipo/'+id, this.opcion());
  }

  activarTipo(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'tipo/activar-tipo/'+id, this.opcion());
  }
}
