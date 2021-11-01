import { IsEmail, IsString, IsNotEmpty } from 'class-validator';

export class UsersDTO {
  @IsString({ message: 'este campo precisa ser uma string' })
  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  name: string;

  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  @IsEmail()
  email: string;

  @IsString({ message: 'este campo precisa ser uma string' })
  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  password: string;

  @IsString({ message: 'este campo precisa ser uma string' })
  telephone?: string;
}
