import { Exclude } from 'class-transformer';
import { Marketplace } from 'src/modules/marketplace/infra/typeorm/entities/Marketplace';
import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  JoinColumn,
  ManyToOne,
} from 'typeorm';
import { Users } from '../../../../users/infra/typeorm/entities/Users';

@Entity('lists')
export class Lists {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  name: string;

  @Exclude()
  @Column({ type: 'float', default: null })
  total_price: number;

  @Exclude()
  @Column({ default: null })
  quantity_products: number;

  @Column({ default: null })
  user_id: string;

  @JoinColumn({ name: 'user_id' })
  @ManyToOne(() => Users, (user) => user.id)
  user: Users;

  @Column({ default: null })
  marketplace_id: string;

  @JoinColumn({ name: 'marketplace_id' })
  @ManyToOne(() => Marketplace, (markeplace) => markeplace.id)
  marketplace: Marketplace;

  @Column({ default: null })
  viewer_id: string;

  @JoinColumn({ name: 'viewer_id' })
  @ManyToOne(() => Users, (user) => user.id)
  viewer: Users;
}
