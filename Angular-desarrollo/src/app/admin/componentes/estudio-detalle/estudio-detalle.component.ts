import { Component, OnInit } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';


// Entidades
import { SolicitudEstudio } from "../../../Entidades/solicitudEstudio";

//Servicio 
import { SolicitudEstudioService } from "../../Servicios/solicitud-estudio.service";


@Component({
  selector: 'app-estudio-detalle',
  templateUrl: './estudio-detalle.component.html',
  styleUrls: ['./estudio-detalle.component.css']
})
export class EstudioDetalleComponent implements OnInit {
  estudio:SolicitudEstudio;
  constructor(private route: ActivatedRoute,
    private location: Location,
    private solicitudServicio:SolicitudEstudioService) {
      this.route.params.pipe(switchMap((params: Params) => { return this.solicitudServicio.getEstudio(params['id']); }))
      .subscribe(x => { this.estudio = x;   });
     }

  ngOnInit(): void {
  }

}
