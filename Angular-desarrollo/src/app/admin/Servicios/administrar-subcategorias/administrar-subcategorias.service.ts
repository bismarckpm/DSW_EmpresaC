import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarSubcategoriasService {

  constructor(public _http: HttpClient) { }

  getAllSubcategorias():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'subcategoria/all');
  }
  addSubcategorias(subcategoriaDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'subcategoria/add',subcategoriaDto);
  }

  updateSubcategoria(id,subcategoriaDto):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'subcategoria/edit/'+id,subcategoriaDto);
  }

  deleteSubcategoria(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'subcategoria/delete/'+id);
  }

  activarSubcategoria(id):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'subcategoria/activar/'+id);
  }
}
