import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { lastValueFrom } from 'rxjs';
import { Activity, ActivityPayloads } from 'src/app/models/activity';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-activity',
  templateUrl: './add-activity.component.html',
  styleUrls: ['./add-activity.component.scss']
})
export class AddActivityComponent implements OnInit {

  user!: User;
  activity!: Activity;
  addActivityForm!: FormGroup;

  constructor(
    private userSrv: UserService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddActivityComponent>
    ) { }

  ngOnInit(): void {
    this.createForm();
  }

  showNotification(message: string) {
    this.snackBar.open(message, 'Chiudi', {
      duration: 3000,
      panelClass: ['custom-close-button'],
    });
  }

  createForm() {
    this.addActivityForm = this.fb.group(
      {
        typeActivity: ['', Validators.required],
        duration: ['', Validators.required],
        distance: [''],
      }
    );
  }

  async onSubmitAddActivity(){

    const typeActivityValue = this.addActivityForm.get('typeActivity')?.value;
    const durationValue = this.addActivityForm.get('duration')?.value;
    const distanceValue = this.addActivityForm.get('distance')?.value;

    const activity: ActivityPayloads = {
      typeActivity: typeActivityValue,
      duration: durationValue,
      distance: distanceValue,
    }

    // try {
    //   let response = await lastValueFrom(this.userSrv.saveActivity(activity));
    //   this.showNotification('Attività salvata con successo');
    //   this.userSrv.getUser().subscribe({
    //     next: (user) => this.user = user,
    //     error: (error) => console.log(error),
    //     complete: () => this.userSrv.userSubject.next(this.user)
    //   })
    // } catch (error) {
    //   console.error('Errore nella chiamata HTTP', error);
    // }
    try {
      let response = await lastValueFrom(this.userSrv.saveActivity(activity));
      this.showNotification('Attività salvata con successo');
      this.dialogRef.close(activity); // Passa l'attività aggiunta al componente genitore
    } catch (error) {
      console.error('Errore nella chiamata HTTP', error);
    }
  }
}
