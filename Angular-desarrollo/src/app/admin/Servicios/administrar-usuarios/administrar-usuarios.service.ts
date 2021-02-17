import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class AdministrarUsuariosService {

  constructor(public _http: HttpClient) { } //Rutas no oficiales

  opcion():any{
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };
    return httpOptions
  }


  addUsuarioAdminAnalista(nuevoUsuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/admin',nuevoUsuarioDto, this.opcion());
  }

  addUsuarioAdminAnalista2(nuevoUsuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/analista',nuevoUsuarioDto, this.opcion());
  }

  addUsuarioCliente(clienteDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'usuario/add/cliente',clienteDto, this.opcion());
  }

  deleteUsuario(id,usuarioDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/delete/'+id,usuarioDto, this.opcion());
  }

  activarUsuario(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.delete(global.url+'usuario/activar/'+id, this.opcion());
  }

  updateUsuario(id,usuarioLdapDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/edit/'+id,usuarioLdapDto, this.opcion());
  }


  updateCliente(id,clienteDto):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'usuario/cliente/edit/'+id,clienteDto, this.opcion());
  }

  getAllUsuarios():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'usuario/all', this.opcion());
  }

  getclienteData(id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'usuario/get-cliente/'+id, this.opcion());
  }

}
