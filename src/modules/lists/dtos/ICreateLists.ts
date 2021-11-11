import { IsNotEmpty, IsString, IsInt } from 'class-validator';

export class ICreateLists {
  @IsNotEmpty({ message: 'o nome não pode ser nulo' })
  @IsString({ message: 'o nome precisa ser uma string' })
  name: string;

  @IsNotEmpty({ message: 'valor não pode ser nulo - user_id' })
  @IsInt({ message: 'o valor é uma string' })
  user_id: string;

  @IsNotEmpty({ message: 'valor não pode ser nulo - products_id' })
  @IsString({ message: 'o valor é uma string' })
  products_id: string[];
}
