import { Component, OnInit } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';


// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";
import { Estudio } from "../../../Entidades/estudio";
//Servicio 
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";
import { Participante } from 'src/app/Entidades/participante';


@Component({
  selector: 'app-estudio-detalle',
  templateUrl: './estudio-detalle.component.html',
  styleUrls: ['./estudio-detalle.component.css']
})
export class EstudioDetalleComponent implements OnInit {
  estudio:Estudio;
  participantes:Participante[];
  error:string;
  activos:number;
  inactivos:number;

  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService) {
      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
      .subscribe(x => { this.estudio = x.estudio;});

      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getParticipantes(params['id']); }))
      .subscribe(x => { this.participantes = x.Participantes; console.log(this.participantes)
      this.activos=this.participantes.filter(x=>x.Estado=="activo").length
      this.inactivos=this.participantes.filter(x=>x.Estado=="inactivo").length
      
      });

     }

  ngOnInit(): void {
  }

  eliminarEstudio(id:number){
    this.solicitudServicio.DeleteEstudios(this.estudio.id).subscribe(x=>{
      console.log(x.codigo)
    })

  }

}
