import { Test, TestingModule } from '@nestjs/testing';
import { ListAllProductsMarketplaceService } from './list-all-products-marketplace.service';

describe('ListAllProductsMarketplaceService', () => {
  let service: ListAllProductsMarketplaceService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ListAllProductsMarketplaceService],
    }).compile();

    service = module.get<ListAllProductsMarketplaceService>(
      ListAllProductsMarketplaceService,
    );
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
