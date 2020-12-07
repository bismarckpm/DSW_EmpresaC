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

  res: {};
  usaurio: usuarioLdap;
  
  constructor(private http: HttpClient) {
    
  }

  loginLdap( usuario: usuarioLdap ){
    this.http.post(global.url+'login/ldap', usuario).toPromise().then( data =>{ this.res = data;} );
    return this.res;
  }
  /*
  metodoGet(){
    this.http.get(this.urlLogin).subscribe((data: any) =>{ console.log(data); this.datajson = data;});
    return this.datajson;
  }
  */
}
