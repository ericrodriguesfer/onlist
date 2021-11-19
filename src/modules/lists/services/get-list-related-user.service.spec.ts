import { Test, TestingModule } from '@nestjs/testing';
import { GetListRelatedUserService } from './get-list-related-user.service';

describe('GetListRelatedUserService', () => {
  let provider: GetListRelatedUserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [GetListRelatedUserService],
    }).compile();

    provider = module.get<GetListRelatedUserService>(GetListRelatedUserService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
