import {
  MigrationInterface,
  QueryRunner,
  Table,
  TableForeignKey,
} from 'typeorm';

export class CreateProductInList1638661032495 implements MigrationInterface {
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.createTable(
      new Table({
        name: 'products_in_list',
        columns: [
          {
            name: 'id',
            type: 'uuid',
            generationStrategy: 'uuid',
            isPrimary: true,
            default: 'uuid_generate_v4()',
          },
          {
            name: 'list_id',
            type: 'uuid',
            isNullable: true,
          },
          {
            name: 'product_id',
            type: 'uuid',
            isNullable: true,
          },
        ],
      }),
    );

    await queryRunner.createForeignKey(
      'products_in_list',
      new TableForeignKey({
        name: 'relationList',
        referencedColumnNames: ['id'],
        columnNames: ['list_id'],
        referencedTableName: 'lists',
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );

    await queryRunner.createForeignKey(
      'products_in_list',
      new TableForeignKey({
        name: 'relationProductInList',
        referencedColumnNames: ['id'],
        columnNames: ['product_id'],
        referencedTableName: 'products',
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey(
      'products_in_list',
      'relationProductInList',
    );
    await queryRunner.dropForeignKey('products_in_list', 'relationList');
    await queryRunner.dropTable('products_in_list');
  }
}
