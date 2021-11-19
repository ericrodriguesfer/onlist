import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { Lists } from '../infra/typeorm/entities/Lists';

interface IListRelatedUserLists {
  user_id: string;
}

@Injectable()
export class GetListRelatedUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
    @InjectRepository(Lists)
    private listRepository: Repository<Lists>,
  ) {}

  async execute({ user_id }: IListRelatedUserLists): Promise<Lists[]> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user)
        throw new NotFoundException('Usuário não encontrado, id inválido');

      const lists = await this.listRepository.find({
        relations: ['user'],
      });

      return lists;
    } catch (err) {
      throw err;
    }
  }
}
