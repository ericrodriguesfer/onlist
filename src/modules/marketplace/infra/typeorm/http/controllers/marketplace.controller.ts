import { Body, Controller, Post, Request } from '@nestjs/common';
import { ICreateMarketplace } from '../../../../dtos/ICreateMarketplace';
import { CreateMarketplaceService } from '../../../../services/create-marketplace.service';
import { Marketplace } from '../../entities/Marketplace';

interface IRequestUser {
  user: {
    id: string;
  };
}

@Controller('marketplace')
export class MarketplaceController {
  constructor(private createMarketplaceService: CreateMarketplaceService) {}

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
}
