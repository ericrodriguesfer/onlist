import { Injectable, UnauthorizedException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Users } from '../infra/typeorm/entities/Users';
import { compare } from 'bcryptjs';
import { JwtService } from '@nestjs/jwt';

interface IAuth {
  email: string;
  password: string;
}

@Injectable()
export class AuthenticateUserService {
  constructor(
    @InjectRepository(Users) private usersRepository: Repository<Users>,
    private jwtService: JwtService,
  ) {}

  async execute({ email, password }: IAuth): Promise<any> {
    const user = await this.usersRepository.findOne({
      where: { email: email },
    });

    if (!user) {
      throw new UnauthorizedException('Usuário não encontrado');
    }

    const comparePassword = await compare(password, user.password);

    if (!comparePassword) {
      throw new UnauthorizedException('Senhas não coincidem');
    }

    return this.jwtService.sign({
      id: user.id,
      name: user.name,
    });
  }
}
