import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UsersController } from './infra/typeorm/controllers/users.controller';
import { Users } from './infra/typeorm/entities/Users';
import { CreateUserService } from './services/create-user.service';

@Module({
  imports: [TypeOrmModule.forFeature([Users])],
  controllers: [UsersController],
  providers: [CreateUserService],
})
export class UsersModule {}
