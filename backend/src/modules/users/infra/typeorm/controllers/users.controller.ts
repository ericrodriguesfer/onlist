import {
  Body,
  ClassSerializerInterceptor,
  Controller,
  Post,
  Put,
  Request,
  UseInterceptors,
  UsePipes,
  ValidationPipe,
} from '@nestjs/common';
import { CreateUserService } from '../../../services/create-user.service';
import { Users } from '../entities/Users';
import { UsersDTO } from '../../../dtos/UsersDTO';
import { ChangeDataUserService } from '../../../services/change-data-user.service';
import { IPutUserDTO } from '../../../dtos/IPutUserDTO';

interface IRequestUser {
  user: {
    id: string;
  };
}

@Controller('users')
export class UsersController {
  constructor(
    private createUserService: CreateUserService,
    private changeDataUserService: ChangeDataUserService,
  ) {}

  @UsePipes(ValidationPipe)
  @UseInterceptors(ClassSerializerInterceptor)
  @Post()
  async createUser(
    @Body() { email, name, password, telephone, initials }: UsersDTO,
  ): Promise<Users> {
    return this.createUserService.execute({
      email,
      name,
      password,
      telephone,
      initials,
    });
  }

  @UseInterceptors(ClassSerializerInterceptor)
  @UsePipes(ValidationPipe)
  @Put()
  async putInfoUser(
    @Request() req: IRequestUser,
    @Body() { name, email, password, telephone, initials }: IPutUserDTO,
  ): Promise<Users> {
    return this.changeDataUserService.execute({
      user_id: req.user.id,
      name,
      email,
      password,
      telephone,
      initials,
    });
  }
}
