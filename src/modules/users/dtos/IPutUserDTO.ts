import { IsString, IsOptional, IsEmail, Length } from 'class-validator';

export class IPutUserDTO {
  @IsString({ message: 'o nome precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  name?: string;

  @IsString({ message: 'o email precisa ser uma string' })
  @IsOptional({ message: 'esse campo não é opcional' })
  @IsEmail()
  email?: string;

  @IsString({ message: 'a senha precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  password?: string;

  @IsString({ message: 'o telefone precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  @Length(11, 11)
  telephone?: string;

  @IsString({ message: 'As iniciais precisam ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  @Length(2, 2)
  initials?: string;
}
