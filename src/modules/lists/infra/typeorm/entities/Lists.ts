import { Exclude } from 'class-transformer';
import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  JoinColumn,
  ManyToOne,
  ManyToMany,
} from 'typeorm';
import { Products } from '../../../../products/infra/typeorm/entities/Products';
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

  @Column({ type: 'text', array: true, default: [] })
  products_id: string[];

  @JoinColumn({ name: 'products_id' })
  @ManyToMany(() => Products)
  products: Products;

  @Column({ default: null })
  user_id: string;

  @JoinColumn({ name: 'user_id' })
  @ManyToOne(() => Users, (user) => user.id)
  user: Users;
}
