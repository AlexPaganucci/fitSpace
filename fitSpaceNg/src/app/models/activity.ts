import { User } from "./user";

export interface Activity {
  id: number;
	typeActivity: string;
	duration: number;
	distance: number;
	calories: number;
	user: User;
}

export interface ActivityPayloads {
  typeActivity: string;
  duration: number;
  distance: number;
}
