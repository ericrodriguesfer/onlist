import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Marketplace } from 'src/modules/marketplace/infra/typeorm/entities/Marketplace';
import { Products } from 'src/modules/products/infra/typeorm/entities/Products';
import { Users } from 'src/modules/users/infra/typeorm/entities/Users';
import { Repository } from 'typeorm';
import { Lists } from '../infra/typeorm/entities/Lists';
import { ProductsInList } from '../infra/typeorm/entities/ProductsInList';

interface IInsertProductInList {
  user_id: string;
  product_id: string;
  list_id: string;
}

@Injectable()
export class InsertProductInListService {
  constructor(
    @InjectRepository(ProductsInList)
    private productInListRepository: Repository<ProductsInList>,
    @InjectRepository(Lists) private listsRepository: Repository<Lists>,
    @InjectRepository(Products)
    private productsRepository: Repository<Products>,
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({
    user_id,
    product_id,
    list_id,
  }: IInsertProductInList): Promise<ProductsInList> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new UnauthorizedException('Usuário não encontrado');
      }

      const list = await this.listsRepository.findOne({
        where: { id: list_id },
      });

      if (!list) {
        throw new NotFoundException('Lista não encontrada');
      }

      const product = await this.productsRepository.findOne({
        where: { id: product_id, marketplace_id: list.marketplace_id },
      });

      if (!product) {
        throw new NotFoundException('Produto não encontrado');
      }

      const productInList = this.productInListRepository.create({
        list_id,
        product_id,
      });

      await this.productInListRepository.save(productInList);

      list.quantity_products++;
      list.total_price += product.price;

      await this.listsRepository.save(list);

      return productInList;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
