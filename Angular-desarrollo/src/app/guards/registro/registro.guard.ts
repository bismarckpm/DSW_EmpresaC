import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistroGuard implements CanActivate {
  constructor(private router:Router){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      var ac=false;
      var rol=localStorage.getItem('rol')

      if(rol){
        if(rol=="encuestado"){
          ac=false
          this.router.navigate(["encuestado/"])
        }
        else if (rol=="admin"){
          ac=false
          this.router.navigate(["admin/"])
        }
        else if(rol=="analista"){
          ac=false
          this.router.navigate(["analista/"])
        }
        else if (rol=="cliente"){
          ac=false
          this.router.navigate(["cliente/"])
        }
        else{
          ac=true
        }

      }
      else{
        ac=true;
        
      }

    return ac;
  }
  
}
