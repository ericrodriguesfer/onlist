import { MigrationInterface, QueryRunner, TableColumn } from 'typeorm';

export class AlterTableUserFixInitialFieldImportant1638046789545
  implements MigrationInterface
{
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropColumn('users', 'initials');
    await queryRunner.addColumn(
      'users',
      new TableColumn({
        name: 'initials',
        type: 'varchar',
        isNullable: false,
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropColumn('users', 'initials');
    await queryRunner.addColumn(
      'users',
      new TableColumn({
        name: 'initials',
        type: 'varchar',
        isNullable: true,
      }),
    );
  }
}
