import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
import { Marketplace } from '../marketplace/infra/typeorm/entities/Marketplace';
import { Products } from './infra/typeorm/entities/Products';
import { ProductsController } from './infra/typeorm/http/controllers/products.controller';
import { CreateProductsService } from './services/create-products.service';

@Module({
  imports: [TypeOrmModule.forFeature([Products, Marketplace])],
  controllers: [ProductsController],
  providers: [CreateProductsService],
})
export class ProductsModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes({ path: 'products/:id', method: RequestMethod.POST });
  }
}
