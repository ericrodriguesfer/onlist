import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
import { Users } from '../users/infra/typeorm/entities/Users';
import { Address } from './infra/typeorm/entities/Address';
import { Marketplace } from './infra/typeorm/entities/Marketplace';
import { MarketplaceController } from './infra/typeorm/http/controllers/marketplace.controller';
import { CreateMarketplaceService } from './services/create-marketplace.service';

@Module({
  imports: [TypeOrmModule.forFeature([Users, Address, Marketplace])],
  controllers: [MarketplaceController],
  providers: [CreateMarketplaceService],
})
export class MarketplaceModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes({ path: 'marketplace', method: RequestMethod.POST });
  }
}
