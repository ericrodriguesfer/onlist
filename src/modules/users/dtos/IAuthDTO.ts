import { IsNotEmpty, IsString, IsEmail } from 'class-validator';

export class IAuth {
  @IsString({ message: 'Este campo precisa ser uma string' })
  @IsEmail()
  @IsNotEmpty({ message: 'Este campo não pode ser nulo' })
  email: string;

  @IsString({ message: 'Este campo precisa ser uma string' })
  @IsNotEmpty({ message: 'Este campo não pode ser nulo' })
  password: string;
}
