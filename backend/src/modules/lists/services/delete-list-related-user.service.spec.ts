import { Test, TestingModule } from '@nestjs/testing';
import { DeleteListRelatedUserService } from './delete-list-related-user.service';

describe('DeleteListRelatedUserService', () => {
  let provider: DeleteListRelatedUserService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [DeleteListRelatedUserService],
    }).compile();

    provider = module.get<DeleteListRelatedUserService>(DeleteListRelatedUserService);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
