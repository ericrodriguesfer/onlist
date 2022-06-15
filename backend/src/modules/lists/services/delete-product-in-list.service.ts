import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Products } from 'src/modules/products/infra/typeorm/entities/Products';
import { Users } from 'src/modules/users/infra/typeorm/entities/Users';
import { Repository } from 'typeorm';
import { Lists } from '../infra/typeorm/entities/Lists';
import { ProductsInList } from '../infra/typeorm/entities/ProductsInList';

interface IDeleteProductInList {
  user_id: string;
  list_id: string;
  product_in_list_id: string;
}

@Injectable()
export class DeleteProductInListService {
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
    list_id,
    product_in_list_id,
  }: IDeleteProductInList): Promise<any> {
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

      const productInList = await this.productInListRepository.findOne({
        where: { id: product_in_list_id },
      });

      if (!productInList) {
        throw new NotFoundException('Produto não encontrado na lista');
      }

      const response = await this.productInListRepository.delete(
        productInList.id,
      );

      if (response.affected === 1) {
        const product = await this.productsRepository.findOne({
          where: { id: productInList.product_id },
        });

        list.quantity_products--;
        list.total_price =
          list.total_price - product.price <= 0
            ? 0
            : list.total_price - product.price;

        await this.listsRepository.save(list);

        return {
          message: 'Produto deletado com sucesso da lista',
        };
      } else {
        return {
          message: 'Não foi possível deletar o produto da lista',
        };
      }
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
