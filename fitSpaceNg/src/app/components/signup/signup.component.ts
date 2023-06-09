import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { lastValueFrom } from 'rxjs';
import { SignupRequest } from 'src/app/models/signup-request';
import { AuthService } from 'src/app/services/auth.service';

const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  hidePassword = true;
  hideConfirmPassword = true;
  signupForm!: FormGroup;

  constructor(
    private authSrv: AuthService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  getErrorMessage() {
    if (this.signupForm.get('email')!.hasError('required')) {
      return 'You must enter an email';
    }

    return this.signupForm.get('email')!.hasError('email')
      ? 'Not a valid email'
      : '';
  }

  passwordsMatch(group: FormGroup): { [key: string]: any } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { notSame: true };
  }

  showNotification(message: string) {
    this.snackBar.open(message, 'Chiudi', {
      duration: 3000,
      panelClass: ['custom-close-button'],
    });
  }

  createForm() {
    this.signupForm = this.fb.group(
      {
        name: ['', Validators.required],
        surname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: [
          '',
          [Validators.required, Validators.pattern(passwordRegex)],
        ],
        confirmPassword: ['', Validators.required],
        weight: ['', Validators.required],
        height: ['', Validators.required],
        birthdate: [null, [Validators.required]],
        typeGoal: ['', Validators.required],
      },
      { validator: this.passwordsMatch }
    );
  }

  async onSubmitSignup() {
    const emailValue = this.signupForm.get('email')?.value;
    const nameValue = this.signupForm.get('name')?.value;
    const surnameValue = this.signupForm.get('surname')?.value;
    const passwordValue = this.signupForm.get('password')?.value;
    const confirmPasswordValue = this.signupForm.get('confirmPassword')?.value;
    const weightValue = this.signupForm.get('weight')?.value;
    const heightValue = this.signupForm.get('height')?.value;
    const birthdateValue = this.signupForm.get('birthdate')?.value;
    const typeGoalValue = this.signupForm.get('typeGoal')?.value;

    const formattedDate = moment(birthdateValue).format('DD/MM/YYYY');

    if (!emailValue || !passwordValue || !confirmPasswordValue) {
      console.error('Errore: email o password non validi');
      return;
    }

    if (passwordValue !== confirmPasswordValue) {
      console.error(
        'Errore: la password e la conferma della password non corrispondono'
      );
      return;
    }

    const signup: SignupRequest = {
      email: emailValue,
      name: nameValue,
      surname: surnameValue,
      password: passwordValue,
      confirmPassword: confirmPasswordValue,
      weight: weightValue,
      height: heightValue,
      birthdate: formattedDate,
      typeGoal: typeGoalValue,
    };

    try {
      let response = await lastValueFrom(this.authSrv.signup(signup));
      this.signupForm.reset();
      this.router.navigate(['/']);
      this.showNotification('Registrazione effettuata con successo');
    } catch (error) {
      console.error('Errore nella chiamata HTTP', error);
    }
  }
}
