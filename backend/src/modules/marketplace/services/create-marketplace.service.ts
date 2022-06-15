import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Users } from 'src/modules/users/infra/typeorm/entities/Users';
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
    @InjectRepository(Users) private usersRepository: Repository<Users>,
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

      const userOwnerMarketplaceExists = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!userOwnerMarketplaceExists) {
        throw new NotFoundException(
          'Não podemos criar este mercado, não localizamos o dono do mesmo em nossa base dedados',
        );
      }

      newMarketplace.user_id = user_id;

      await this.marketplaceRepository.save(newMarketplace);

      return newMarketplace;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
