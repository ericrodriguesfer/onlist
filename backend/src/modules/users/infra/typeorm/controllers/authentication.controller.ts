import {
  Body,
  ClassSerializerInterceptor,
  Controller,
  Post,
  UseInterceptors,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { AuthenticateUserService } from '../../../services/authenticate-user.service';
import { IAuth } from '../../../dtos/IAuthDTO';

@Controller('authentication')
export class AuthenticationController {
  constructor(private authenticateUserService: AuthenticateUserService) {}

  @UsePipes(ValidationPipe)
  @UseInterceptors(ClassSerializerInterceptor)
  @Post()
  async createSession(@Body() { email, password }: IAuth): Promise<any> {
    return this.authenticateUserService.execute({ email, password });
  }
}
