/* eslint-disable prettier/prettier */
import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Lists } from './modules/lists/infra/typeorm/entities/Lists';
import { ProductsInList } from './modules/lists/infra/typeorm/entities/ProductsInList';
import { ListsModule } from './modules/lists/lists.module';
import { Address } from './modules/marketplace/infra/typeorm/entities/Address';
import { Marketplace } from './modules/marketplace/infra/typeorm/entities/Marketplace';
import { MarketplaceModule } from './modules/marketplace/marketplace.module';
import { Products } from './modules/products/infra/typeorm/entities/Products';
import { ProductsModule } from './modules/products/products.module';
import { Users } from './modules/users/infra/typeorm/entities/Users';
import { UsersModule } from './modules/users/users.module';

@Module({
  imports: [
    ConfigModule.forRoot(),
    TypeOrmModule.forRoot({
      type: process.env.TYPEORM_CONNECTION as any,
      host: process.env.TYPEORM_HOST,
      port: parseInt(process.env.TYPEORM_PORT),
      database: process.env.TYPEORM_DATABASE,
      username: process.env.TYPEORM_USERNAME,
      password: process.env.TYPEORM_PASSWORD,
      entities: [Users, Marketplace, Address, Products, Lists, ProductsInList],
      retryDelay: 3000,
      retryAttempts: 10,
      synchronize: true,
    }),
    UsersModule,
    MarketplaceModule,
    ProductsModule,
    ListsModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
