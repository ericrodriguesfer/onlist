import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Marketplace } from 'src/modules/marketplace/infra/typeorm/entities/Marketplace';
import { Repository } from 'typeorm';
import { Products } from '../infra/typeorm/entities/Products';

interface IListAllProductsByMarketplace {
  marketplace_id: string;
}

@Injectable()
export class ListAllProductsMarketplaceService {
  constructor(
    @InjectRepository(Products)
    private productsRepository: Repository<Products>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
  ) {}

  async execute({
    marketplace_id,
  }: IListAllProductsByMarketplace): Promise<Products[]> {
    try {
      const marketplace = await this.marketplaceRepository.findOne({
        where: { id: marketplace_id },
      });

      if (!marketplace) {
        throw new NotFoundException('Mercado não encontrado');
      }

      const products = await this.productsRepository.find({
        where: { marketplace_id: marketplace.id },
      });

      return products;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
