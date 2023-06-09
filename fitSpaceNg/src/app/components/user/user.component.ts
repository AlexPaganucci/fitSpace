import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { ReccomendedActivitiesComponent } from '../reccomended-activities/reccomended-activities.component';
import { AddActivityComponent } from '../add-activity/add-activity.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  user!: User;

  constructor(private userSrv: UserService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadUserActivities();
  }

  loadUserActivities(): void {
    this.userSrv.getUser().subscribe({
      next: (user) => {
        this.user = user;
        this.updateActivityNames();
      },
      error: (error) => console.log(error)
    });
  }

  updateActivityNames(): void {
    this.user.activities.forEach((activity) => {
      switch(activity.typeActivity){
        case 'RUN':
          activity.typeActivity = "CORSA";
          break;
        case 'WALK':
          activity.typeActivity = "CAMMINATA";
          break;
        case 'CYCLING':
          activity.typeActivity = "BICICLETTA";
          break;
        case 'SWIM':
          activity.typeActivity = "NUOTO";
          break;
        case 'GYM':
          activity.typeActivity = "PALESTRA";
          break;
        default:
          break;
      }
    });
  }

  openRecommendedActivities(){
    this.dialog.open(ReccomendedActivitiesComponent);
  }

  openAddActivity(): void {
    this.dialog.open(AddActivityComponent).afterClosed().subscribe(() => {
      this.loadUserActivities();
    });
  }
}
