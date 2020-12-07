import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { map } from 'rxjs/operators'; 

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public urlLogin:string = 'http://localhost:8080/pruebaORM-1.0-SNAPSHOT/api/login/ldap';

  res: {};
  usaurio: usuarioLdap;
  
  
  constructor(private http: HttpClient) {
    
  }

  loginLdap( usuario: usuarioLdap ){
    this.http.post(this.urlLogin, usuario).toPromise().then( data =>{ this.res = data;} );
    return this.res;
  }
  /*
  metodoGet(){
    this.http.get(this.urlLogin).subscribe((data: any) =>{ console.log(data); this.datajson = data;});
    return this.datajson;
  }
  */
}
