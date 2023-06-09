import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { ConfirmLogoutComponent } from '../confirm-logout/confirm-logout.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  @Input() toggleDrawerFn!: Function;
  @Input() isLargeScreen!: boolean;
  isLogged = false;

  constructor(private authSrv: AuthService, public dialog: MatDialog) { }

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

  openLogoutModal() {
    this.dialog.open(ConfirmLogoutComponent);
  }

}
