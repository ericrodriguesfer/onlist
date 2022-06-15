import { Test, TestingModule } from '@nestjs/testing';
import { ListMarketRelatedUserService } from './list-market-related-user.service';

describe('ListMarketRelatedUserService', () => {
  let provider: ListMarketRelatedUserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ListMarketRelatedUserService],
    }).compile();

    provider = module.get<ListMarketRelatedUserService>(ListMarketRelatedUserService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
