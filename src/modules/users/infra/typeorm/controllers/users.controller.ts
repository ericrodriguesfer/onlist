import {
  Body,
  ClassSerializerInterceptor,
  Controller,
  Post,
  UseInterceptors,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { CreateUserService } from '../../../services/create-user.service';
import { Users } from '../entities/Users';
import { UsersDTO } from '../../../dtos/UsersDTO';

@Controller('users')
export class UsersController {
  constructor(private createUserService: CreateUserService) {}

  @UsePipes(ValidationPipe)
  @UseInterceptors(ClassSerializerInterceptor)
  @Post()
  async createUser(
    @Body() { email, name, password, telephone }: UsersDTO,
  ): Promise<Users> {
    return this.createUserService.execute({ email, name, password, telephone });
  }
}
