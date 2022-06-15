import { IsNotEmpty, IsOptional, IsString, Length } from 'class-validator';

export class IPutMarketplace {
  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  @Length(8, 8)
  cep: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  city: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  district: string;

  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  latitude: number;

  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  longitude: number;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  name: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  number: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  state: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsOptional({ message: 'este campo é opcional' })
  street: string;

  @IsString({ message: 'esse campo é uma string(texto)' })
  @IsOptional({ message: 'este campo é opcional' })
  pathImage: string;
}
