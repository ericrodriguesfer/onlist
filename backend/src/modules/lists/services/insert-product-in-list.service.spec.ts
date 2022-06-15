import { Test, TestingModule } from '@nestjs/testing';
import { InsertProductInListService } from './insert-product-in-list.service';

describe('InsertProductInListService', () => {
  let service: InsertProductInListService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [InsertProductInListService],
    }).compile();

    service = module.get<InsertProductInListService>(
      InsertProductInListService,
    );
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
