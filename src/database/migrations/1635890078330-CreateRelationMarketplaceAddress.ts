import { MigrationInterface, QueryRunner, TableForeignKey } from 'typeorm';

export class CreateRelationMarketplaceAddress1635890078330
  implements MigrationInterface
{
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.createForeignKey(
      'marketplace',
      new TableForeignKey({
        name: 'relationAddressInMarketplace',
        referencedColumnNames: ['id'],
        columnNames: ['address_id'],
        referencedTableName: 'address',
        onDelete: 'CASCADE',
        onUpdate: 'CASCADE',
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropForeignKey(
      'marketplace',
      'relationAddressInMarketplace',
    );
  }
}
