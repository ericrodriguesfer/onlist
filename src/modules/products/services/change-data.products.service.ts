import {
  Injectable,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { Products } from '../infra/typeorm/entities/Products';

interface IPutProducts {
  user_id: string;
  product_id: string;
  name?: string;
  price?: number;
}

@Injectable()
export class ChangeDataProducts {
  constructor(
    @InjectRepository(Products)
    private productsRepository: Repository<Products>,
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({
    user_id,
    name,
    price,
    product_id,
  }: IPutProducts): Promise<Products> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user)
        throw new UnauthorizedException('Usuário não autorizado, id inválido');

      const product = await this.productsRepository.findOne({
        where: { id: product_id },
      });

      if (!product) throw new NotFoundException('Produto não encontrado');

      product.name = name;
      product.price = price;

      await this.productsRepository.save(product);

      return product;
    } catch (err) {
      throw err;
    }
  }
}
