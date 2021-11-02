import { IsInt, IsString, IsNotEmpty, IsOptional } from 'class-validator';

export class ICreateMarketplace {
  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  name: string;

  @IsInt()
  @IsNotEmpty({ message: 'a latitude não pode ser nula' })
  latitude: number;

  @IsInt()
  @IsNotEmpty({ message: 'a longitude não pode ser nula' })
  longitude: number;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsOptional()
  pathImage: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'a rua não pode ser nula' })
  street: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o estado não pode ser nulo' })
  state: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o cep não pode ser nulo' })
  cep: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o número da casa não poder ser nulo' })
  number: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'a cidade não pode ser nula' })
  city: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o bairro não pode ser nulo' })
  district: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o user_id não pode ser nulo' })
  user_id: string;
}
