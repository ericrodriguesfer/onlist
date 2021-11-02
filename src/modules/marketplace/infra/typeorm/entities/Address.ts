import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity('address')
export class Address {
  @PrimaryGeneratedColumn('uuid')
  id: string;

  @Column()
  state: string;

  @Column()
  street: string;

  @Column()
  cep: string;

  @Column()
  number: string;

  @Column()
  city: string;

  @Column()
  district: string;
}
