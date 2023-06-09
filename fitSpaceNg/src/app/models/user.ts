export interface User {
  id: number;
  email: string;
  name: string;
  surname: string;
  password: string;
  roles: string[];
  birthdate: Date;
}
