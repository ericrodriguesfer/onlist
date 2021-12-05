import {
  Body,
  Controller,
  Get,
  Param,
  Post,
  Put,
  Request,
} from '@nestjs/common';
import { ICreateProducts } from '../../../../dtos/ICreateProducts';
import { ChangeDataProducts } from '../../../../services/change-data.products.service';
import { CreateProductsService } from '../../../../services/create-products.service';
import { ListAllProductsMarketplaceService } from '../../../../services/list-all-products-marketplace.service';
import { Products } from '../../entities/Products';

interface IRequestUser {
  user: {
    id: string;
  };
}

interface IPutProducts {
  user_id: string;
  product_id: string;
  name?: string;
  price?: number;
}

@Controller('products')
export class ProductsController {
  constructor(
    private createProductsService: CreateProductsService,
    private changeDataProductsService: ChangeDataProducts,
    private listAllProductsByMarketplace: ListAllProductsMarketplaceService,
  ) {}

  @Get('/:id')
  async getProductsByMarketplace(
    @Param('id') marketplace_id: string,
  ): Promise<Products[]> {
    return this.listAllProductsByMarketplace.execute({ marketplace_id });
  }

  @Post('/:id')
  async createProducts(
    @Param('id') marketplace_id: string,
    @Body() { name, price }: ICreateProducts,
  ): Promise<Products> {
    return this.createProductsService.execute({ name, marketplace_id, price });
  }

  @Put('/:id')
  async putDataProducts(
    @Param('id') product_id: string,
    @Request() req: IRequestUser,
    @Body() { name, price }: IPutProducts,
  ): Promise<Products> {
    return this.changeDataProductsService.execute({
      product_id,
      name,
      price,
      user_id: req.user.id,
    });
  }
}
