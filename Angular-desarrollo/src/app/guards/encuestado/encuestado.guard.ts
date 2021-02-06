import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EncuestadoGuard implements CanActivate {
  constructor(private router:Router){

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      var ac=false;
      var rol=localStorage.getItem('rol')

      if(rol){
        if(rol=="encuestado"){
          ac=true
        }
        else{
          ac=false
          this.router.navigate(["401"])
        }

      }
      else{
        ac=false;
        this.router.navigate([""])
      }

    return ac;
  }
  
}
