import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/home/';

@Injectable({
  providedIn: 'root'
})
export class UserPageService {

  constructor(private http : HttpClient) { }

  getFriends(userId: string) : Observable<any>{
    return this.http.get(API_URL + 'friends?userId='+userId, { responseType: 'json' })
  }

  addToFried(userId: number, friendId: number){
    return this.http.post(API_URL + 'addToFried', {userId, friendId})
  }

  removeFriend(userId: number, friendId: number){
    return this.http.post(API_URL + 'removeFriend', {userId, friendId})
  }
}
