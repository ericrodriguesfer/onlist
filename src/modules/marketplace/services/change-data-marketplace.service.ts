import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { Address } from '../infra/typeorm/entities/Address';
import { Marketplace } from '../infra/typeorm/entities/Marketplace';

interface IPutMarketplace {
  user_id: string;
  marketplace_id: string;
  name?: string;
  cep?: string;
  city?: string;
  district?: string;
  latitude?: number;
  longitude?: number;
  number?: string;
  state?: string;
  street?: string;
}

@Injectable()
export class ChangeDataMarketplaceService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
    @InjectRepository(Address) private addressRepository: Repository<Address>,
  ) {}

  async execute({
    user_id,
    marketplace_id,
    cep,
    city,
    district,
    latitude,
    longitude,
    name,
    number,
    state,
  }: IPutMarketplace): Promise<Marketplace> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user)
        throw new NotFoundException('Usuário não encontrado, id inválido');

      const market = await this.marketplaceRepository.findOne({
        where: { id: marketplace_id },
      });

      const addressOfMarket = await this.addressRepository.findOne({
        where: { id: market.address_id },
      });

      if (market) {
        market.name = name;
        market.latitude = latitude;
        market.longitude = longitude;
        addressOfMarket.cep = cep;
        addressOfMarket.district = district;
        addressOfMarket.number = number;
        addressOfMarket.state = state;
        addressOfMarket.city = city;
        await this.marketplaceRepository.save(market);
        await this.addressRepository.save(addressOfMarket);
      } else {
        throw new NotFoundException('Mercado não encontrado, id inválido');
      }
      return market;
    } catch (err) {
      throw err;
    }
  }
}
