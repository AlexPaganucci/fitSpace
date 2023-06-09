import { Activity } from "./activity";
import { Goal } from "./goal";

export interface User {
  id: number;
  email: string;
  name: string;
  surname: string;
  password: string;
  roles: string[];
  weight: number;
  height: number;
  goal: Goal;
  birthdate: Date;
  activities: Activity[]
}
