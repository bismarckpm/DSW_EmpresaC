import { Component, OnInit } from '@angular/core';

import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-participantes-estudio',
  templateUrl: './participantes-estudio.component.html',
  styleUrls: ['./participantes-estudio.component.css']
})
export class ParticipantesEstudioComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {
  }

}
