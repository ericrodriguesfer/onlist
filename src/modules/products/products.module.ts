import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
import { Marketplace } from '../marketplace/infra/typeorm/entities/Marketplace';
import { Users } from '../users/infra/typeorm/entities/Users';
import { Products } from './infra/typeorm/entities/Products';
import { ProductsController } from './infra/typeorm/http/controllers/products.controller';
import { ChangeDataProducts } from './services/change-data.products.service';
import { CreateProductsService } from './services/create-products.service';
import { ListAllProductsMarketplaceService } from './services/list-all-products-marketplace.service';

@Module({
  imports: [TypeOrmModule.forFeature([Products, Marketplace, Users])],
  controllers: [ProductsController],
  providers: [
    CreateProductsService,
    ChangeDataProducts,
    ListAllProductsMarketplaceService,
  ],
})
export class ProductsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes(
        { path: 'products/:id', method: RequestMethod.GET },
        { path: 'products/:id', method: RequestMethod.POST },
        { path: 'products/:id', method: RequestMethod.PUT },
      );
  }
}
