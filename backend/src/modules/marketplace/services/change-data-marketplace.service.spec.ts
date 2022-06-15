import { Test, TestingModule } from '@nestjs/testing';
import { ChangeDataMarketplaceService } from './change-data-marketplace.service';

describe('ChangeDataMarketplaceService', () => {
  let provider: ChangeDataMarketplaceService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ChangeDataMarketplaceService],
    }).compile();

    provider = module.get<ChangeDataMarketplaceService>(ChangeDataMarketplaceService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
