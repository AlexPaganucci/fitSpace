import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  hide = true;
  email = new FormControl("", [Validators.required, Validators.email]);
  password = new FormControl("", Validators.required);
  errorMessage!: string;

  constructor(private authSrv: AuthService, private snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'Devi inserire un valore';
    }

    return this.email.hasError('email') ? 'Email non valida' : "";
  }

  showNotification(message: string) {
    this.snackBar.open(message, 'Chiudi', {
      duration: 3000,
      panelClass: ['custom-close-button'],
    });
  }

  async onSubmit(){
    const emailValue = this.email.value;
    const passwordValue = this.password.value;

    if (!emailValue || !passwordValue) {
      console.error('Errore: email o password non validi');
      return;
    }

    const login: LoginRequest = {
      email: emailValue,
      password: passwordValue
    };

    try {
      let response = await lastValueFrom(this.authSrv.login(login));
      this.email.reset();
      this.password.reset();
      this.router.navigate(['/']);
      this.showNotification("Login effettuato con successo");
    }catch (error: any) {
      console.error('Errore nella chiamata HTTP', error);
      if (error.status === 401) {
        this.errorMessage = 'Username o password non validi. Riprova.';
      } else {
        this.errorMessage = 'Errore durante l\'accesso al sistema. Riprova più tardi.';
      }
    }
  }


}
