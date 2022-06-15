import { IsString, IsNotEmpty } from 'class-validator';

export class ICreateProducts {
  @IsString({ message: 'esse produto deve ter nome em string' })
  @IsNotEmpty({ message: 'esse produto não pode ter nome vazio' })
  name: string;

  @IsNotEmpty({ message: 'esse produto não pode ter preço vazio' })
  price: number;

  @IsNotEmpty({ message: 'pra criar um produto, precisa de um id' })
  @IsString({ message: 'o id do marketplace é uma string' })
  marketplace_id: string;
}
