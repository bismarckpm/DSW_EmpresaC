import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';


@Injectable({
  providedIn: 'root'
})
export class SolicitudEstudiosService {

  constructor(public _http: HttpClient) { }

  opcion(){
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
    return this._http.get(global.url+'marca/all' , this.opcion());
  }

  getAllCategorias():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'categoria/all' , this.opcion());
    
  }

  getAllSubcategorias():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'subcategoria/all' , this.opcion());
    
  }

  getAllPaises():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'pais/all', this.opcion());
    
  }

  getAllEstados():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'estado/all' , this.opcion());
    
  }

  getAllCiudades():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'ciudad/all' , this.opcion());
    
  }

  getAllParroquias():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'parroquia/all' , this.opcion());
    
  }

  getAllNivelAcademicos():Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'niveles_academicos/all', this.opcion());  
    
  }

  getClienteIdByUsuario(cliente_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'cliente/get-id/'+cliente_id , this.opcion());  
    
  }

  doSolicitudEstudio(solicitud):Observable<any>{
    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.post(global.url+'solicitud/add',solicitud , this.opcion());
  }
}




