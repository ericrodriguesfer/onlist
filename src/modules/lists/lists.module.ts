import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
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
export class ListsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes({ path: '/lists', method: RequestMethod.POST });
  }
}
