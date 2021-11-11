import { Test, TestingModule } from '@nestjs/testing';
import { CreateListsService } from './create-lists.service';

describe('CreateListsService', () => {
  let provider: CreateListsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [CreateListsService],
    }).compile();

    provider = module.get<CreateListsService>(CreateListsService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
