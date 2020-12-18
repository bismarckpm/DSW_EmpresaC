import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { map } from 'rxjs/operators'; 
import {global} from 'src/urlGlobal';

@Injectable({
  providedIn: 'root'
})
export class RecupService {

  constructor(private http: HttpClient) { }

  recup( usuario: usuarioLdap ): Observable<any> {
    return this.http.post(global.url+'recuperacion', usuario);
  }
}
