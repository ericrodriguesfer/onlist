import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Request,
} from '@nestjs/common';
import { DeleteProductInListService } from 'src/modules/lists/services/delete-product-in-list.service';
import { GetListRelatedViewerInListService } from 'src/modules/lists/services/get-list-related-viewer-in-list.service';
import { InsertProductInListService } from 'src/modules/lists/services/insert-product-in-list.service';
import { ListProductInListService } from 'src/modules/lists/services/list-product-in-list.service';
import { Products } from 'src/modules/products/infra/typeorm/entities/Products';
import { ICreateLists } from '../../../../dtos/ICreateLists';
import { CreateListsService } from '../../../../services/create-lists.service';
import { DeleteListRelatedUserService } from '../../../../services/delete-list-related-user.service';
import { GetListRelatedUserService } from '../../../../services/get-list-related-user.service';
import { Lists } from '../../entities/Lists';
import { ProductsInList } from '../../entities/ProductsInList';

interface IRequestUser {
  user: {
    id: string;
  };
}

interface IInsertProductInList {
  product_id: string;
  list_id: string;
}

@Controller('lists')
export class ListsController {
  constructor(
    private createListService: CreateListsService,
    private listRelatedUserService: GetListRelatedUserService,
    private deleteListService: DeleteListRelatedUserService,
    private insertProductInListService: InsertProductInListService,
    private listProductInListService: ListProductInListService,
    private deleteProductInListService: DeleteProductInListService,
    private listRelatedUserViewerListService: GetListRelatedViewerInListService,
  ) {}

  @Get()
  async getListRelatedUser(@Request() req: IRequestUser): Promise<Lists[]> {
    return this.listRelatedUserService.execute({ user_id: req.user.id });
  }

  @Get('/viewer')
  async getListViewerInList(@Request() req: IRequestUser): Promise<Lists[]> {
    return this.listRelatedUserViewerListService.execute({
      user_id: req.user.id,
    });
  }

  @Get('/:id')
  async getListProductsInList(
    @Request() req: IRequestUser,
    @Param('id') list_id: string,
  ): Promise<Products[]> {
    return this.listProductInListService.execute({
      user_id: req.user.id,
      list_id,
    });
  }

  @Post()
  async createList(
    @Request() req: IRequestUser,
    @Body() { name, marketplace_id, viewer_id }: ICreateLists,
  ): Promise<any> {
    return this.createListService.execute({
      name,
      marketplace_id,
      user_id: req.user.id,
      viewer_id,
    });
  }

  @Post('/insert')
  async insertProductInList(
    @Request() req: IRequestUser,
    @Body() { product_id, list_id }: IInsertProductInList,
  ): Promise<ProductsInList> {
    return this.insertProductInListService.execute({
      user_id: req.user.id,
      product_id,
      list_id,
    });
  }

  @Delete('/:id')
  async deleteList(
    @Request() req: IRequestUser,
    @Param('id') list_id: string,
  ): Promise<any> {
    return this.deleteListService.execute({ user_id: req.user.id, list_id });
  }

  @Delete('/:list/:product')
  async deleteProductInList(
    @Request() req: IRequestUser,
    @Param('list') list_id: string,
    @Param('product') product_id: string,
  ): Promise<any> {
    return this.deleteProductInListService.execute({
      user_id: req.user.id,
      list_id,
      product_in_list_id: product_id,
    });
  }
}
