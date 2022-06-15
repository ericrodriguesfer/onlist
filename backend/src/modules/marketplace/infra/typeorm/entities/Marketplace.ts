import {
  Entity,
  Column,
  PrimaryGeneratedColumn,
  JoinColumn,
  OneToOne,
  ManyToOne,
} from 'typeorm';
import { Users } from '../../../../users/infra/typeorm/entities/Users';
import { Address } from './Address';

@Entity('marketplace')
export class Marketplace {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  name: string;

  @Column({ type: 'float8' })
  latitude: number;

  @Column({ type: 'float8' })
  longitude: number;

  @Column({ default: null })
  pathImage: string;

  @Column({ default: null })
  address_id: string;

  @OneToOne(() => Address)
  @JoinColumn({ name: 'address_id' })
  address: Address;

  @Column({ default: null })
  user_id: string;

  @ManyToOne(() => Users, (users) => users.id)
  @JoinColumn({ name: 'user_id' })
  user: Users;
}
