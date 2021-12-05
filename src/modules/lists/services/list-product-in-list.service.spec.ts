import { Test, TestingModule } from '@nestjs/testing';
import { ListProductInListService } from './list-product-in-list.service';

describe('ListProductInListService', () => {
  let service: ListProductInListService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ListProductInListService],
    }).compile();

    service = module.get<ListProductInListService>(ListProductInListService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
