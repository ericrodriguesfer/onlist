import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Products } from '../products/infra/typeorm/entities/Products';
import { Users } from '../users/infra/typeorm/entities/Users';
import { Lists } from './infra/typeorm/entities/Lists';
import { ListsController } from './infra/typeorm/http/controllers/lists.controller';
import { CreateListsService } from './services/create-lists.service';

@Module({
  imports: [TypeOrmModule.forFeature([Users, Products, Lists])],
  controllers: [ListsController],
  providers: [CreateListsService],
})
export class ListsModule {}
