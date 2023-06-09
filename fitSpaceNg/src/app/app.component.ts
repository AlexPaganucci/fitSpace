import { Component, HostListener, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmLogoutComponent } from './components/confirm-logout/confirm-logout.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  title = 'fitSpaceNg';
  isLargeScreen!: boolean;
  showFiller = false;
  isLogged = false;

  constructor(private authSrv: AuthService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.onResize();
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


  @HostListener('window:resize')
  onResize() {
    this.isLargeScreen = window.innerWidth > 765;
  }

  openLogoutModal() {
    this.dialog.open(ConfirmLogoutComponent);
  }
}
