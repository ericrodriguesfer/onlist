import {
  Injectable,
  InternalServerErrorException,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Marketplace } from 'src/modules/marketplace/infra/typeorm/entities/Marketplace';
import { Repository } from 'typeorm';
import { Users } from '../../users/infra/typeorm/entities/Users';
import { ICreateLists } from '../dtos/ICreateLists';
import { Lists } from '../infra/typeorm/entities/Lists';

@Injectable()
export class CreateListsService {
  constructor(
    @InjectRepository(Lists) private listsRepository: Repository<Lists>,
    @InjectRepository(Marketplace)
    private marketplaceRepository: Repository<Marketplace>,
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({
    name,
    marketplace_id,
    user_id,
    viewer_id,
  }: ICreateLists): Promise<Lists> {
    try {
      const existsListByName = await this.listsRepository.findOne({
        where: { name: name },
      });

      if (existsListByName) {
        throw new UnauthorizedException('Já existe uma lista com esse nome');
      }

      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new NotFoundException(
          'Não foi possível encontrar o usuário, id inválido',
        );
      }

      const marketplace = await this.marketplaceRepository.findOne({
        where: { id: marketplace_id, user_id: user.id },
      });

      if (!marketplace) {
        throw new UnauthorizedException('O mercado não foi encontrado');
      }

      if (viewer_id) {
        if (user.id === viewer_id) {
          throw new UnauthorizedException(
            'O proprietário da lista não pode ser visualizador da mesma',
          );
        }

        const existsUserViewer = await this.usersRepository.findOne({
          where: { id: viewer_id },
        });

        if (!existsUserViewer) {
          throw new NotFoundException('O visualizador não foi encontrado');
        }

        const list = this.listsRepository.create({
          name,
          user_id,
          marketplace_id,
          viewer_id: existsUserViewer.id,
          quantity_products: 0,
          total_price: 0,
        });

        await this.listsRepository.save(list);

        return list;
      } else {
        const list = this.listsRepository.create({
          name,
          user_id,
          marketplace_id,
          quantity_products: 0,
          total_price: 0,
        });

        await this.listsRepository.save(list);

        return list;
      }
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
