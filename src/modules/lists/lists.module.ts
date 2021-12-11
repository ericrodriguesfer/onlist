import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
import { Marketplace } from '../marketplace/infra/typeorm/entities/Marketplace';
import { Products } from '../products/infra/typeorm/entities/Products';
import { Users } from '../users/infra/typeorm/entities/Users';
import { Lists } from './infra/typeorm/entities/Lists';
import { ProductsInList } from './infra/typeorm/entities/ProductsInList';
import { ListsController } from './infra/typeorm/http/controllers/lists.controller';
import { CreateListsService } from './services/create-lists.service';
import { DeleteListRelatedUserService } from './services/delete-list-related-user.service';
import { GetListRelatedUserService } from './services/get-list-related-user.service';
import { InsertProductInListService } from './services/insert-product-in-list.service';
import { ListProductInListService } from './services/list-product-in-list.service';
import { DeleteProductInListService } from './services/delete-product-in-list.service';
import { GetListRelatedViewerInListService } from './services/get-list-related-viewer-in-list.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([
      Users,
      Products,
      Lists,
      Marketplace,
      ProductsInList,
    ]),
  ],
  controllers: [ListsController],
  providers: [
    CreateListsService,
    GetListRelatedUserService,
    DeleteListRelatedUserService,
    InsertProductInListService,
    ListProductInListService,
    DeleteProductInListService,
    GetListRelatedViewerInListService,
  ],
})
export class ListsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes(
        { path: '/lists', method: RequestMethod.GET },
        { path: '/lists/viewer', method: RequestMethod.GET },
        { path: '/lists/:id', method: RequestMethod.GET },
        { path: '/lists', method: RequestMethod.POST },
        { path: '/lists/insert', method: RequestMethod.POST },
        { path: '/lists/:id', method: RequestMethod.DELETE },
        { path: '/lists/:list/:product', method: RequestMethod.DELETE },
      );
  }
}
