import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { TypeOrmModule } from '@nestjs/typeorm';
import auth from '../../config/auth';
import { AuthenticationController } from './infra/typeorm/controllers/authentication.controller';
import { UsersController } from './infra/typeorm/controllers/users.controller';
import { Users } from './infra/typeorm/entities/Users';
import { AuthenticateUserService } from './services/authenticate-user.service';
import { CreateUserService } from './services/create-user.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Users]),
    JwtModule.register({
      secret: auth.secret,
      signOptions: { expiresIn: auth.expiresIn },
    }),
  ],
  controllers: [UsersController, AuthenticationController],
  providers: [CreateUserService, AuthenticateUserService],
})
export class UsersModule {}
