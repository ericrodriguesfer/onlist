import { Test, TestingModule } from '@nestjs/testing';
import { ChangeDataUserService } from './change-data-user.service';

describe('ChangeDataUserService', () => {
  let provider: ChangeDataUserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ChangeDataUserService],
    }).compile();

    provider = module.get<ChangeDataUserService>(ChangeDataUserService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
