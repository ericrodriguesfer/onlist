import { Injectable, UnauthorizedException } from '@nestjs/common';
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
  }: IPutUserDTO): Promise<Users> {
    try {
      const user = await this.usersRepository.findOne({
        where: { id: user_id },
      });

      console.log('pos busca', user);

      if (!user) throw new UnauthorizedException('Usuário com id inválido');

      user.email = email;
      user.name = name;
      user.telephone = telephone;

      if (password) {
        const newHashedPass = await hash(password, 8);
        user.password = newHashedPass;
        await this.usersRepository.save(user);
      }
      await this.usersRepository.save(user);

      return user;
    } catch (err) {
      throw err;
    }
  }
}
