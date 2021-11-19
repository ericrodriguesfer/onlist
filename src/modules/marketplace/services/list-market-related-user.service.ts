import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { Marketplace } from '../infra/typeorm/entities/Marketplace';

interface IListRelatedUserMarket {
  user_id: string;
}

@Injectable()
export class ListMarketRelatedUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
  ) {}

  async execute({ user_id }: IListRelatedUserMarket): Promise<Marketplace[]> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user)
        throw new NotFoundException('Usuário não encontrado, id inválido');

      const marketplaceList = await this.marketplaceRepository.find({
        relations: ['user'],
      });

      return marketplaceList;
    } catch (err) {
      throw err;
    }
  }
}
