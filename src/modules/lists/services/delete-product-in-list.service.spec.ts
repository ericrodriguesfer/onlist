import { Test, TestingModule } from '@nestjs/testing';
import { DeleteProductInListService } from './delete-product-in-list.service';

describe('DeleteProductInListService', () => {
  let service: DeleteProductInListService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [DeleteProductInListService],
    }).compile();

    service = module.get<DeleteProductInListService>(
      DeleteProductInListService,
    );
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
