import { Body, Controller, Post, Request } from '@nestjs/common';
import { ICreateLists } from '../../../../dtos/ICreateLists';
import { CreateListsService } from '../../../../services/create-lists.service';

interface IRequestUser {
  user: {
    id: string;
  };
}

@Controller('lists')
export class ListsController {
  constructor(private createListService: CreateListsService) {}

  @Post()
  async createList(
    @Request() req: IRequestUser,
    @Body() { name, products_id }: ICreateLists,
  ): Promise<any> {
    console.log('id do user', req.user.id);
    return this.createListService.execute({
      name,
      products_id,
      user_id: req.user.id,
    });
  }
}
