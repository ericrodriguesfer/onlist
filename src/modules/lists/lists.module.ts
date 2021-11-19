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
import { DeleteListRelatedUserService } from './services/delete-list-related-user.service';
import { GetListRelatedUserService } from './services/get-list-related-user.service';

@Module({
  imports: [TypeOrmModule.forFeature([Users, Products, Lists])],
  controllers: [ListsController],
  providers: [
    CreateListsService,
    GetListRelatedUserService,
    DeleteListRelatedUserService,
  ],
})
export class ListsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes(
        { path: '/lists', method: RequestMethod.POST },
        { path: '/lists', method: RequestMethod.GET },
        { path: '/lists/:id', method: RequestMethod.DELETE },
      );
  }
}
