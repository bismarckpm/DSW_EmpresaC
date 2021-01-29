import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';


@Injectable({
  providedIn: 'root'
})
export class AdminMarcasService {

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

  getAllMarcas():Observable<any>{


    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'marca/all', this.opcion());
  }
  addMarca(marcaDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'marca/add',marcaDto, this.opcion());
  }

  updateMarca(id,marcaDto):Observable<any>{


    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'marca/edit/'+id,marcaDto, this.opcion());
  }

  deleteMarca(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'marca/delete/'+id, this.opcion());
  }

  activarMarca(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'marca/activar/'+id, this.opcion());
  }
}
