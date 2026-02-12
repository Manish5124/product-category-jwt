import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  constructor(private http:HttpClient) { }

  helloUser(){
    return this.http.get(environment.apiBaseUrl+"/api/user/hello")
  }

  helloAdmin(){
    return this.http.get(environment.apiBaseUrl+"/api/admin/hello")
  }

}
