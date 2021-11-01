import { Test, TestingModule } from '@nestjs/testing';
import { AuthenticateUserService } from './authenticate-user.service';

describe('AuthenticateUserService', () => {
  let provider: AuthenticateUserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AuthenticateUserService],
    }).compile();

    provider = module.get<AuthenticateUserService>(AuthenticateUserService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
