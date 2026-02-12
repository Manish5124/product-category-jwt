import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  constructor(private http:HttpClient,private authService:AuthService) { }

  helloUser(){

    const headers = new HttpHeaders({'Authorization':'Bearer '+this.authService.token})

    return this.http.get<{message: string}>(environment.apiBaseUrl+"/api/user/hello",{headers:headers})
  }

  helloAdmin(){
    const headers = new HttpHeaders({'Authorization':'Bearer '+this.authService.token})
    return this.http.get<{message: string}>(environment.apiBaseUrl+"/api/admin/hello",{headers:headers})
  }

}
