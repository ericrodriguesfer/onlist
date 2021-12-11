import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Users } from 'src/modules/users/infra/typeorm/entities/Users';
import { Repository } from 'typeorm';
import { Lists } from '../infra/typeorm/entities/Lists';

interface IUserViewer {
  user_id: string;
}

@Injectable()
export class GetListRelatedViewerInListService {
  constructor(
    @InjectRepository(Lists) private listsRepository: Repository<Lists>,
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({ user_id }: IUserViewer): Promise<Lists[]> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new NotFoundException('Usuário não encontrado');
      }

      const lists = await this.listsRepository.find({
        where: { viewer_id: user.id },
      });

      return lists;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
