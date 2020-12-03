import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgProgress } from 'ngx-progressbar';
import {NgProgressRef} from 'ngx-progressbar';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'Angular-desarrollo';
  progressRef: NgProgressRef;

  constructor(private progress: NgProgress, private _http:HttpClient) {
    this.progressRef = this.progress.ref('myProgress');
  }
  
  ngOnInit() {

    this.startLoading();
    this._http.get('ttp://slowwly.robertomurray.co.uk/delay/3000/url/https://jsonplaceholder.typicode.com/posts/1');
    setTimeout(()=>{
      this.completeLoading();
    },3000);

  }

  startLoading() {
    this.progressRef.start();
  }

  completeLoading() {
    this.progressRef.complete();
  }

}
