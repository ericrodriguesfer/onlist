import { Injectable, UnauthorizedException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ICreateMarketplace } from '../dtos/ICreateMarketplace';
import { Address } from '../infra/typeorm/entities/Address';
import { Marketplace } from '../infra/typeorm/entities/Marketplace';

@Injectable()
export class CreateMarketplaceService {
  constructor(
    @InjectRepository(Address) private addressRepository: Repository<Address>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
  ) {}

  async execute({
    name,
    pathImage,
    cep,
    city,
    district,
    latitude,
    longitude,
    number,
    state,
    street,
    user_id,
  }: ICreateMarketplace): Promise<Marketplace> {
    try {
      const marketplace = await this.marketplaceRepository.findOne({
        where: { name },
      });

      if (marketplace) {
        throw new UnauthorizedException(
          'Não podemos criar um novo mercado, já um com nome existente',
        );
      }

      const address = this.addressRepository.create({
        cep,
        city,
        district,
        number,
        state,
        street,
      });

      await this.addressRepository.save(address);

      const newMarketplace = this.marketplaceRepository.create({
        address_id: address.id,
        name,
        latitude,
        longitude,
        pathImage,
      });

      newMarketplace.user_id = user_id;

      await this.marketplaceRepository.save(newMarketplace);

      return newMarketplace;
    } catch (err) {
      throw err;
    }
  }
}
