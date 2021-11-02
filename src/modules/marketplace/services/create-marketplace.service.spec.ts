import { Test, TestingModule } from '@nestjs/testing';
import { CreateMarketplaceService } from './create-marketplace.service';

describe('CreateMarketplaceService', () => {
  let provider: CreateMarketplaceService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [CreateMarketplaceService],
    }).compile();

    provider = module.get<CreateMarketplaceService>(CreateMarketplaceService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
