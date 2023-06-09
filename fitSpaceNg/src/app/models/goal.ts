import { User } from "./user";

export interface Goal {
  id: number;
  user: User;
  typeGoal: string;
  recommendedActivities: string[];
}
