import {
  MigrationInterface,
  QueryRunner,
  TableColumn,
  TableForeignKey,
} from 'typeorm';

export class AlterTableListDeleteFieldAndAddField1638658600749
  implements MigrationInterface
{
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey('lists', 'relationProductsInList');
    await queryRunner.dropColumn('lists', 'products_id');
    await queryRunner.addColumn(
      'lists',
      new TableColumn({
        name: 'marketplace_id',
        type: 'uuid',
        isNullable: true,
      }),
    );
    await queryRunner.createForeignKey(
      'lists',
      new TableForeignKey({
        name: 'relationMarketplaceInList',
        referencedTableName: 'marketplace',
        referencedColumnNames: ['id'],
        columnNames: ['marketplace_id'],
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey('lists', 'relationMarketplaceInList');
    await queryRunner.dropColumn('lists', 'marketplace_id');
    await queryRunner.addColumn(
      'lists',
      new TableColumn({
        name: 'products_id',
        type: 'uuid',
        isNullable: true,
      }),
    );
    await queryRunner.createForeignKey(
      'lists',
      new TableForeignKey({
        name: 'relationProductsInList',
        referencedTableName: 'products',
        referencedColumnNames: ['id'],
        columnNames: ['products_id'],
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }
}
