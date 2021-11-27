import { IsEmail, IsString, IsNotEmpty, Length } from 'class-validator';

export class UsersDTO {
  @IsString({ message: 'este campo precisa ser uma string' })
  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  name: string;

  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  @IsEmail()
  email: string;

  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  password: string;

  @IsString({ message: 'este campo precisa ser uma string' })
  @Length(11, 11)
  telephone?: string;

  @IsString({ message: 'este campo precisa ser uma string' })
  @IsNotEmpty({ message: 'este campo não pode ser nulo' })
  @Length(2, 2)
  initials: string;
}
