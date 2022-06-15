import {
  Injectable,
  InternalServerErrorException,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../infra/typeorm/entities/Users';
import { hash } from 'bcryptjs';
import { UsersDTO } from '../dtos/UsersDTO';

@Injectable()
export class CreateUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
  ) {}

  async execute({
    name,
    email,
    password,
    telephone,
    initials,
  }: UsersDTO): Promise<Users> {
    try {
      const user = await this.usersRepository.findOne({
        where: { email: email },
      });

      if (user) {
        throw new UnauthorizedException(
          'Usuário com credenciais já cadastradas',
        );
      }

      const hashedPass = await hash(password, 8);

      const newUser = this.usersRepository.create({
        name,
        email,
        password: hashedPass,
        telephone,
        initials,
      });

      await this.usersRepository.save(newUser);

      return newUser;
    } catch (err) {
      if (err) throw err;
      throw new InternalServerErrorException(
        'Desculpa, houve um erro em processar essa solicitação',
      );
    }
  }
}
