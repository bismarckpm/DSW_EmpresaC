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
  addMarca(marcaDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'marca/add',marcaDto);
  }

  updateMarca(id,marcaDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'marca/edit/'+id,marcaDto);
  }

  deleteMarca(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'marca/delete/'+id);
  }

  activarMarca(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'marca/activar/'+id);
  }
}
