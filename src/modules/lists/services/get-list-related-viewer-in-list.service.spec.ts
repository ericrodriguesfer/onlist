import { Test, TestingModule } from '@nestjs/testing';
import { GetListRelatedViewerInListService } from './get-list-related-viewer-in-list.service';

describe('GetListRelatedViewerInListService', () => {
  let service: GetListRelatedViewerInListService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [GetListRelatedViewerInListService],
    }).compile();

    service = module.get<GetListRelatedViewerInListService>(
      GetListRelatedViewerInListService,
    );
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
