import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { global } from 'src/urlGlobal';

//entidades
import { Respuesta } from "../../../Entidades/respuesta";

@Injectable({
  providedIn: 'root'
})
export class ConsultaEstudiosService {

  constructor(public _http: HttpClient) { }

  getAllEstudios(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'analista/estudios/'+ _id);
  }

  go(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'analista/empezar-estudio/'+ _id,_id);
  }

  eliminar_participacion(_id):Observable<any>{
      //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
      return this._http.put(global.url+'analista/eliminar-participacion/'+ _id,_id);
    
  }
  getEstudiosTelefonicos(_id):Observable<Respuesta>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get<Respuesta>(global.url+'analista/estudios-telefono/'+ _id);
  }

  getEstudio(id:number): Observable<Respuesta> {
    

    return this._http.get<Respuesta>(global.url + 'admin/estudio/'+id)
    
  }

  getEstudioPreguntas(id,id2): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this._http.get<Respuesta>(global.url + 'encuestado/pregunta-estudio/'+id+"/"+id2)
  }


  Responder(id1, id2,id3, objeto): Observable<Respuesta> {


    return this._http.put<Respuesta>(global.url+"encuestado/Respuesta/"+id1+"/"+id2+"/"+id3,objeto)
  }


  cerrarParticipacion(id,id2): Observable<Respuesta> {
    
    return this._http.delete<Respuesta>(global.url + 'encuestado/finalizar/'+id+"/"+id2)
  }


  darResultados(objeto): Observable<Respuesta> {
    
    return this._http.put<Respuesta>(global.url + 'analista/responder-solicitud', objeto)
  }
}
