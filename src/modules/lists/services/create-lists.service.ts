import {
  Injectable,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Products } from '../../products/infra/typeorm/entities/Products';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { ICreateLists } from '../dtos/ICreateLists';
import { Lists } from '../infra/typeorm/entities/Lists';

@Injectable()
export class CreateListsService {
  constructor(
    @InjectRepository(Lists) private listsRepository: Repository<Lists>,
    @InjectRepository(Products)
    private productsRepository: Repository<Products>,
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({ name, products_id, user_id }: ICreateLists): Promise<any> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user)
        throw new NotFoundException(
          'Não foi possível encontrar o usuário, id inválido',
        );

      const products = await this.productsRepository.find({
        where: { id: products_id },
      });

      let count = 0;

      for (let i = 0; i < products.length; i++) {
        count++;
      }

      if (products === null)
        throw new UnauthorizedException('Produtos não encontrados');

      const list = this.listsRepository.create({
        name,
        user_id,
        products_id,
        quantity_products: count,
      });

      await this.listsRepository.save(list);

      return { list };
    } catch (err) {
      throw err;
    }
  }
}
