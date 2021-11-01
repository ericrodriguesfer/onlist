import { Body, Controller, Post } from '@nestjs/common';
import { AuthenticateUserService } from '../../../services/authenticate-user.service';

interface IAuth {
  email: string;
  password: string;
}

@Controller('authentication')
export class AuthenticationController {
  constructor(private authenticateUserService: AuthenticateUserService) {}

  @Post()
  async createSession(@Body() { email, password }: IAuth): Promise<any> {
    return this.authenticateUserService.execute({ email, password });
  }
}
