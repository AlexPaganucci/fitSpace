import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  isLogged = false;

  constructor(private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.auth$.subscribe(token => {
      if (token) {
        this.isLogged = true;
      } else {
        this.isLogged = false;
      }
    });
    if (this.authSrv.isLogged() && this.authSrv.checkTokenValidity()) {
      this.isLogged = true;
    }
  }

}
