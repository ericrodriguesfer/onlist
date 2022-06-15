import {
  MigrationInterface,
  QueryRunner,
  Table,
  TableForeignKey,
} from 'typeorm';

export class CreateMarketplace1635889457865 implements MigrationInterface {
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.createTable(
      new Table({
        name: 'marketplace',
        columns: [
          {
            name: 'id',
            type: 'uuid',
            generationStrategy: 'uuid',
            isPrimary: true,
            default: 'uuid_generate_v4()',
          },
          {
            name: 'name',
            type: 'varchar',
          },
          {
            name: 'latitude',
            type: 'float',
          },
          {
            name: 'longitude',
            type: 'float',
          },
          {
            name: 'address_id',
            type: 'uuid',
            isNullable: true,
          },
          {
            name: 'user_id',
            type: 'uuid',
            isNullable: true,
          },
          {
            name: 'pathImage',
            type: 'varchar',
          },
        ],
      }),
    );

    await queryRunner.createForeignKey(
      'marketplace',
      new TableForeignKey({
        name: 'relationMarketToUser',
        referencedColumnNames: ['id'],
        columnNames: ['user_id'],
        referencedTableName: 'users',
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey('marketplace', 'relationMarketToUser');
    await queryRunner.dropTable('marketplace');
  }
}
