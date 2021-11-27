import {
  BadRequestException,
  Injectable,
  InternalServerErrorException,
  NotFoundException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { hash } from 'bcryptjs';
import { Repository } from 'typeorm';
import { Users } from '../infra/typeorm/entities/Users';

interface IPutUserDTO {
  user_id: string;
  name: string;
  email: string;
  password: string;
  telephone: string;
  initials: string;
}

@Injectable()
export class ChangeDataUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({
    user_id,
    name,
    email,
    password,
    telephone,
    initials,
  }: IPutUserDTO): Promise<Users> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      if (!user) {
        throw new NotFoundException('Usuário não encontrado');
      }

      if (email && email != user.email) {
        const existsOtherAccountWithEmailRepassed =
          await this.usersRepository.findOne({
            where: { email: email },
          });

        if (existsOtherAccountWithEmailRepassed) {
          throw new BadRequestException(
            'Já existe outro usuário com este email',
          );
        } else {
          user.email = email;
        }
      }

      user.name = name;
      user.initials = initials;

      if (telephone && telephone != user.telephone) {
        const existsOtherAccountWithTelephoneRepassed =
          await this.usersRepository.findOne({
            where: { telephone: telephone },
          });

        if (existsOtherAccountWithTelephoneRepassed) {
          throw new BadRequestException(
            'Já existe outro usuário com este número de telefone',
          );
        } else {
          user.telephone = telephone;
        }
      }

      if (password) {
        const newHashedPass = await hash(password, 8);
        user.password = newHashedPass;
        await this.usersRepository.save(user);
      }

      await this.usersRepository.save(user);

      return user;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
