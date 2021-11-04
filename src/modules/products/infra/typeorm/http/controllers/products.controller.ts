import { Body, Controller, Param, Post } from '@nestjs/common';
import { ICreateProducts } from '../../../../dtos/ICreateProducts';
import { CreateProductsService } from '../../../../services/create-products.service';
import { Products } from '../../entities/Products';

@Controller('products')
export class ProductsController {
  constructor(private createProductsService: CreateProductsService) {}

  @Post('/:id')
  async createProducts(
    @Param('id') marketplace_id: string,
    @Body() { name, price }: ICreateProducts,
  ): Promise<Products> {
    return this.createProductsService.execute({ name, marketplace_id, price });
  }
}
