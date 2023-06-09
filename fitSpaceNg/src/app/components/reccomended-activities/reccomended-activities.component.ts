import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reccomended-activities',
  templateUrl: './reccomended-activities.component.html',
  styleUrls: ['./reccomended-activities.component.scss']
})
export class ReccomendedActivitiesComponent implements OnInit {

  user!: User;
  constructor(private userSrv: UserService) { }

  ngOnInit(): void {
    this.userSrv.getUser().subscribe({
      next: (user) => this.user = user,
      error: (error) => console.log(error),
    })
  }

}
