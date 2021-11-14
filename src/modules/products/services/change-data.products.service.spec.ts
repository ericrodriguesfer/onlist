import { Test, TestingModule } from '@nestjs/testing';
import { ChangeDataProducts.Service } from './change-data.products.service';

describe('ChangeDataProducts.Service', () => {
  let provider: ChangeDataProducts.Service;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ChangeDataProducts.Service],
    }).compile();

    provider = module.get<ChangeDataProducts.Service>(ChangeDataProducts.Service);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
