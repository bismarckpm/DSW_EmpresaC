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

  deleteUsuario(id,usuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/delete/'+id,usuarioDto);
  }

  updateUsuario(id,usuarioLdapDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/edit/'+id,usuarioLdapDto);
  }


  updateCliente(id,clienteDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/cliente/edit/'+id,clienteDto);
  }

  getAllUsuarios():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'usuario/all');
  }

  getclienteData(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'usuario/get-cliente/'+id);
  }

}
