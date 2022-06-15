import {
  Injectable,
  InternalServerErrorException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { Lists } from '../infra/typeorm/entities/Lists';

interface IDeleteList {
  user_id: string;
  list_id: string;
}

@Injectable()
export class DeleteListRelatedUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
    @InjectRepository(Lists) private listsRepository: Repository<Lists>,
  ) {}

  async execute({ list_id, user_id }: IDeleteList): Promise<any> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new UnauthorizedException('Usuário não encontrado, id inválido');
      }

      const list = await this.listsRepository.findOne({
        where: { id: list_id },
      });

      if (!list) {
        throw new UnauthorizedException(
          'Não é possível deletar a lista, id inválido',
        );
      }

      const response = await this.listsRepository.delete(list.id);

      if (response.affected === 1) {
        return {
          message: 'Lista deletada com sucesso',
        };
      } else {
        return {
          message: 'Não foi possível deletar a lista',
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
