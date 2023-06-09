import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, filter, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONST_UTENTE } from './auth.service';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl;
  userSubject = new BehaviorSubject<User | null>(null);
  user$: Observable<User> = this.userSubject.asObservable().pipe(
    filter((user: User | null): user is User => user !== null)
  );

  constructor(private http: HttpClient) { }

  getUser(): Observable<User> {
    const token = sessionStorage.getItem(CONST_UTENTE);
    const userId = token ? JSON.parse(token).id : null;
    return this.http.get<User>(`${this.apiUrl}/user/${userId}`).pipe(
      tap(user => this.userSubject.next(user))
    )
  }
}
