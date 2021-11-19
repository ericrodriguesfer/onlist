import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Request,
} from '@nestjs/common';
import { ICreateLists } from '../../../../dtos/ICreateLists';
import { CreateListsService } from '../../../../services/create-lists.service';
import { DeleteListRelatedUserService } from '../../../../services/delete-list-related-user.service';
import { GetListRelatedUserService } from '../../../../services/get-list-related-user.service';
import { Lists } from '../../entities/Lists';

interface IRequestUser {
  user: {
    id: string;
  };
}

@Controller('lists')
export class ListsController {
  constructor(
    private createListService: CreateListsService,
    private listRelatedUserService: GetListRelatedUserService,
    private deleteListService: DeleteListRelatedUserService,
  ) {}

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

  @Get()
  async getListRelatedUser(@Request() req: IRequestUser): Promise<Lists[]> {
    return this.listRelatedUserService.execute({ user_id: req.user.id });
  }

  @Delete('/:id')
  async deleteList(
    @Request() req: IRequestUser,
    @Param('id') list_id: string,
  ): Promise<any> {
    return this.deleteListService.execute({ user_id: req.user.id, list_id });
  }
}
