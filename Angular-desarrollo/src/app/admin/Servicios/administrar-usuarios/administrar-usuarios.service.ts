import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarUsuariosService {

  constructor(public _http: HttpClient) { } //Rutas no oficiales


  addUsuarioAdminAnalista(nuevoUsuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/admin',nuevoUsuarioDto);
  }

  addUsuarioAdminAnalista2(nuevoUsuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/analista',nuevoUsuarioDto);
  }

  addUsuarioCliente(clienteDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/cliente',clienteDto);
  }
  
  getAllUsuarios():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'usuario/all');
  }

  deleteUsuario(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'usuario/delete/'+id);
  }
}
