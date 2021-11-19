import {
  Body,
  Controller,
  Get,
  Param,
  Post,
  Put,
  Request,
} from '@nestjs/common';
import { ICreateMarketplace } from '../../../../dtos/ICreateMarketplace';
import { ChangeDataMarketplaceService } from '../../../../services/change-data-marketplace.service';
import { CreateMarketplaceService } from '../../../../services/create-marketplace.service';
import { ListMarketRelatedUserService } from '../../../../services/list-market-related-user.service';
import { Marketplace } from '../../entities/Marketplace';

interface IPutMarketplace {
  user_id: string;
  marketplace_id: string;
  name?: string;
  cep?: string;
  city?: string;
  district?: string;
  latitude?: number;
  longitude?: number;
  number?: string;
  state?: string;
  street?: string;
}

interface IRequestUser {
  user: {
    id: string;
  };
}

@Controller('marketplace')
export class MarketplaceController {
  constructor(
    private createMarketplaceService: CreateMarketplaceService,
    private listMarketplaceUserService: ListMarketRelatedUserService,
    private changeDataMarketPlaceService: ChangeDataMarketplaceService,
  ) {}

  @Post()
  async createMarketplace(
    @Request() req: IRequestUser,
    @Body()
    {
      cep,
      city,
      district,
      latitude,
      longitude,
      name,
      number,
      pathImage,
      state,
      street,
    }: ICreateMarketplace,
  ): Promise<Marketplace> {
    console.log('user id reques', req.user.id);
    return this.createMarketplaceService.execute({
      cep,
      city,
      district,
      latitude,
      longitude,
      name,
      number,
      pathImage,
      state,
      street,
      user_id: req.user.id,
    });
  }

  @Get('/list')
  async listMarketRelatedUser(
    @Request() req: IRequestUser,
  ): Promise<Marketplace[]> {
    return this.listMarketplaceUserService.execute({ user_id: req.user.id });
  }

  @Put('/put/:id')
  async changeDataMarketplace(
    @Request() req: IRequestUser,
    @Param('id') marketplace_id: string,
    @Body()
    {
      cep,
      city,
      district,
      latitude,
      longitude,
      name,
      number,
      state,
      street,
    }: IPutMarketplace,
  ): Promise<Marketplace> {
    return this.changeDataMarketPlaceService.execute({
      user_id: req.user.id,
      marketplace_id,
      cep,
      city,
      district,
      latitude,
      longitude,
      name,
      number,
      state,
      street,
    });
  }
}
