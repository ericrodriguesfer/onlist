import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Marketplace } from '../../marketplace/infra/typeorm/entities/Marketplace';
import { ICreateProducts } from '../dtos/ICreateProducts';
import { Products } from '../infra/typeorm/entities/Products';

@Injectable()
export class CreateProductsService {
  constructor(
    @InjectRepository(Products)
    private productsRepository: Repository<Products>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
  ) {}

  async execute({
    name,
    price,
    marketplace_id,
  }: ICreateProducts): Promise<Products> {
    try {
      const product = await this.productsRepository.findOne({
        where: { name },
      });

      if (product) {
        throw new UnauthorizedException(
          'Não é possível cadastrar um produto com o mesmo nome',
        );
      }

      const marketplace = await this.marketplaceRepository.findOne({
        where: { id: marketplace_id },
      });

      if (!marketplace) {
        throw new NotFoundException(
          'Não achamos o id do mercado para criar o produto',
        );
      }

      const newProduct = this.productsRepository.create({
        name,
        price,
        marketplace_id,
      });

      await this.productsRepository.save(newProduct);

      return newProduct;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
