import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  JoinColumn,
  ManyToMany,
} from 'typeorm';
import { Products } from 'src/modules/products/infra/typeorm/entities/Products';
import { Lists } from './Lists';

@Entity('products_in_list')
export class ProductsInList {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column({ default: null })
  list_id: string;

  @JoinColumn({ name: 'list_id' })
  @ManyToMany(() => Lists, (list) => list.id)
  list: Lists;

  @Column({ default: null })
  product_id: string;

  @JoinColumn({ name: 'product_id' })
  @ManyToMany(() => Products, (product) => product.id)
  product: Products;
}
