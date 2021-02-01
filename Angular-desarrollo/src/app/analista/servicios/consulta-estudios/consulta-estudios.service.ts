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

  opcion(){
    const Ltoken= localStorage.getItem("token")
    const httpOptions = {
      headers: new HttpHeaders({
        'authorization':  Ltoken
      })
    };
    return httpOptions
  }

  getAllEstudios(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'analista/estudios/'+ _id , this.opcion());
  }

  go(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.put(global.url+'analista/empezar-estudio/'+ _id,_id, this.opcion());
  }

  eliminar_participacion(_id):Observable<any>{
      //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
      return this._http.put(global.url+'analista/eliminar-participacion/'+ _id,_id, this.opcion());
    
  }
  getEstudiosTelefonicos(_id):Observable<Respuesta>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get<Respuesta>(global.url+'analista/estudios-telefono/'+ _id, this.opcion());
  }

  getEstudio(id:number): Observable<Respuesta> {
    

    return this._http.get<Respuesta>(global.url + 'admin/estudio/'+id, this.opcion());
    
  }

  getEstudioPreguntas(id,id2): Observable<Respuesta> {
    // return of(DISHES.filter((dish) => (dish.id === id))[0]).pipe(delay(2000));
    return this._http.get<Respuesta>(global.url + 'encuestado/pregunta-estudio/'+id+"/"+id2, this.opcion());
  }


  Responder(id1, id2,id3, objeto): Observable<Respuesta> {


    return this._http.put<Respuesta>(global.url+"encuestado/Respuesta/"+id1+"/"+id2+"/"+id3,objeto, this.opcion());
  }


  cerrarParticipacion(id,id2): Observable<Respuesta> {
    
    return this._http.delete<Respuesta>(global.url + 'encuestado/finalizar/'+id+"/"+id2, this.opcion());
  }

  getRespuestasEstudio(estudio_id):Observable<any>{
    return this._http.get(global.url+'analista/respuestas-estudio/'+estudio_id, this.opcion());

  }


  darResultados(objeto): Observable<Respuesta> {
  
    return this._http.put<Respuesta>(global.url + 'analista/responder-solicitud', objeto, this.opcion());
  }

  getGraficos(estudio_id): Observable<any> {
    
    return this._http.get(global.url + 'analista/graficos-estudio/'+estudio_id, this.opcion());
  }

  getRespuestaAnalista(_id):Observable<any>{

    //let headers=new HttpHeaders().set('content-Type','application/x-www-form-urlencoded');
    return this._http.get(global.url+'cliente/respuesta-analista/'+ _id, this.opcion());
  }
}
