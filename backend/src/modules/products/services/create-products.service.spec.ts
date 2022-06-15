import { Test, TestingModule } from '@nestjs/testing';
import { CreateProductsService } from './create-products.service';

describe('CreateProductsService', () => {
  let provider: CreateProductsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [CreateProductsService],
    }).compile();

    provider = module.get<CreateProductsService>(CreateProductsService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
