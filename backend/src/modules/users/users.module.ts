import {
  MiddlewareConsumer,
  Module,
  NestModule,
  RequestMethod,
} from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { TypeOrmModule } from '@nestjs/typeorm';
import auth from '../../config/auth';
import ensureAuthenticatedMiddleware from '../../shared/ensureAuthenticatedMiddleware';
import { AuthenticationController } from './infra/typeorm/controllers/authentication.controller';
import { UsersController } from './infra/typeorm/controllers/users.controller';
import { Users } from './infra/typeorm/entities/Users';
import { AuthenticateUserService } from './services/authenticate-user.service';
import { ChangeDataUserService } from './services/change-data-user.service';
import { CreateUserService } from './services/create-user.service';

@Module({
  imports: [
    TypeOrmModule.forFeature([Users]),
    JwtModule.register({
      secret: auth.secret,
      signOptions: { expiresIn: auth.expiresIn },
    }),
  ],
  controllers: [UsersController, AuthenticationController],
  providers: [
    CreateUserService,
    AuthenticateUserService,
    ChangeDataUserService,
  ],
})
export class UsersModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer
      .apply(ensureAuthenticatedMiddleware)
      .forRoutes({ path: '/users', method: RequestMethod.PUT });
  }
}
