import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { map } from 'rxjs/operators'; 
import {global} from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  opcion(){
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };

    return httpOptions

  }

  loginLdap( usuario: usuarioLdap): Observable<any> {
    return this.http.post(global.url+'login/ldap', usuario);
  }
  /*
  metodoGet(){
    this.http.get(this.urlLogin).subscribe((data: any) =>{ console.log(data); this.datajson = data;});
    return this.datajson;
  }
  */

  changePassword(changePasswordDto):Observable<any>{
  //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
  return this.http.post(global.url+'usuario/change-password',changePasswordDto, this.opcion());
  }


  verificartoken():Observable<any>{

  //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
  return this.http.get(global.url+'prueba/seguridad', this.opcion());
  }

  logOut():Observable<any>{
    console.log("logout")
    const id= localStorage.getItem("user_id")

    localStorage.removeItem("user_id")
    localStorage.removeItem("rol")
    localStorage.removeItem("token")

    return this.http.delete(global.url+'login/logout/'+id);
  }


}
