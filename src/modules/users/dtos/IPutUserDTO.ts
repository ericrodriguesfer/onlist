import { IsString, IsOptional, IsNotEmpty } from 'class-validator';

export class IPutUserDTO {
  @IsString({ message: 'o nome precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  name?: string;

  @IsString({ message: 'o email precisa ser uma string' })
  @IsOptional({ message: 'esse campo não é opcional' })
  email?: string;

  @IsString({ message: 'a senha precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  password?: string;

  @IsString({ message: 'o telefone precisa ser uma string' })
  @IsOptional({ message: 'esse campo é opcional' })
  telephone?: string;
}
