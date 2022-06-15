import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  JoinColumn,
  ManyToOne,
} from 'typeorm';
import { Marketplace } from '../../../../marketplace/infra/typeorm/entities/Marketplace';

@Entity('products')
export class Products {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  name: string;

  @Column({ type: 'float' })
  price: number;

  @Column({ default: null })
  marketplace_id: string;

  @ManyToOne(() => Marketplace)
  @JoinColumn({ name: 'marketplace_id' })
  marketplace: Marketplace;
}
