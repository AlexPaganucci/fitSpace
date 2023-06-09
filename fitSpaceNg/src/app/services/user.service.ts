import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, filter, switchMap, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONST_UTENTE } from './auth.service';
import { User } from '../models/user';
import { Activity, ActivityPayloads } from '../models/activity';

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

  saveActivity(activityPayloads: ActivityPayloads): Observable<Activity> {
    return this.getUser().pipe(
      switchMap((user) => {
        const userId = user.id;
        console.log(user);
        if (userId === undefined) {
          console.log("errore: non è stato trovato l'id dello User");
          return throwError("Errore: non è stato trovato l'id dello User");
        }
        return this.http.post<Activity>(`${this.apiUrl}/activity/save/${userId}`, activityPayloads);
      }),
      catchError((error) => {
        console.log(error);
        return throwError(error);
      })
    );
  }
}
