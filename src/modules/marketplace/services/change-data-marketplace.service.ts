import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
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
  pathImage?: string;
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
    street,
    pathImage,
  }: IPutMarketplace): Promise<Marketplace> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new NotFoundException('Usuário não encontrado, id inválido');
      }

      const market = await this.marketplaceRepository.findOne({
        where: { id: marketplace_id },
      });

      if (!market) {
        throw new NotFoundException(
          'Mercado não encontrado, atualização cancelada',
        );
      }

      if (name != market.name) {
        const existsOtherMarkeplaceWithNameRepassed =
          await this.marketplaceRepository.findOne({
            where: { name: name },
          });

        if (existsOtherMarkeplaceWithNameRepassed) {
          throw new UnauthorizedException(
            'Não podemos atualizar este mercado para esse nome, já há um existente com esse nome',
          );
        }
      }

      const addressOfMarket = await this.addressRepository.findOne({
        where: { id: market.address_id },
      });

      if (!addressOfMarket) {
        throw new NotFoundException(
          'Endereço do mercado não encontrado, atualização cancelada',
        );
      }

      market.name = name;
      market.latitude = latitude;
      market.longitude = longitude;
      market.pathImage = pathImage;

      addressOfMarket.cep = cep;
      addressOfMarket.district = district;
      addressOfMarket.number = number;
      addressOfMarket.state = state;
      addressOfMarket.city = city;
      addressOfMarket.street = street;

      await this.marketplaceRepository.save(market);
      await this.addressRepository.save(addressOfMarket);

      return market;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
