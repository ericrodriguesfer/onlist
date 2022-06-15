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
import { ChangeDataMarketplaceService } from './services/change-data-marketplace.service';
import { CreateMarketplaceService } from './services/create-marketplace.service';
import { ListMarketRelatedUserService } from './services/list-market-related-user.service';

@Module({
  imports: [TypeOrmModule.forFeature([Users, Address, Marketplace])],
  controllers: [MarketplaceController],
  providers: [
    CreateMarketplaceService,
    ListMarketRelatedUserService,
    ChangeDataMarketplaceService,
  ],
})
export class MarketplaceModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes(
        { path: 'marketplace', method: RequestMethod.POST },
        { path: 'marketplace/list', method: RequestMethod.GET },
        { path: 'marketplace/:id', method: RequestMethod.PUT },
      );
  }
}
